package de.metas.bpartner.impl;

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IClientDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BP_Relation;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_Shipper;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import de.metas.bpartner.BPartnerContacts;
import de.metas.bpartner.BPartnerLocations;
import de.metas.bpartner.IBPartnerAware;
import de.metas.bpartner.IBPartnerDAO;
import de.metas.bpartner.exceptions.OrgHasNoBPartnerLinkException;
import de.metas.bpartner.model.BPartner;
import de.metas.logging.LogManager;

public class BPartnerDAO implements IBPartnerDAO
{
	private static final Logger logger = LogManager.getLogger(BPartnerDAO.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public BPartner retrieveBPartnerAgg(final Properties ctx, final int bpartnerId)
	{
		final I_C_BPartner bpartnerData = retrieveBPartner(ctx, bpartnerId);
		if(bpartnerData == null)
		{
			return null;
		}
		
		final Supplier<BPartnerLocations> locations = () -> retrieveLocations(ctx, bpartnerId, ITrx.TRXNAME_None);
		final Supplier<BPartnerContacts> contacts = () -> retrieveContactsAgg(ctx, bpartnerId, ITrx.TRXNAME_None);
		return new BPartner(bpartnerData, locations, contacts);
	}

	@Override
	public BPartner toBPartnerAgg(final I_C_BPartner bpartnerData)
	{
		if(bpartnerData == null)
		{
			return null;
		}
		
		final Supplier<BPartnerLocations> locations = () -> retrieveLocations(Env.getCtx(), bpartnerData.getC_BPartner_ID(), ITrx.TRXNAME_None);
		final Supplier<BPartnerContacts> contacts = () -> retrieveContactsAgg(Env.getCtx(), bpartnerData.getC_BPartner_ID(), ITrx.TRXNAME_None);
		return new BPartner(bpartnerData, locations, contacts);
	}

	@Override
	public BPartner retrieveBPartnerAggForModel(final Object model)
	{
		// 09527 get the most suitable language:
		final IBPartnerAware bpartnerAware = InterfaceWrapperHelper.asColumnReferenceAwareOrNull(model, IBPartnerAware.class);
		if (bpartnerAware == null)
		{
			return null;
		}

		final int bpartnerId = bpartnerAware.getC_BPartner_ID();
		if (bpartnerId <= 0)
		{
			return null;
		}

		return retrieveBPartnerAgg(Env.getCtx(), bpartnerId);
	}

	
	@Override
	public void save(final BPartner bpartner)
	{
		Services.get(ITrxManager.class)
				.run(ITrx.TRXNAME_ThreadInherited, localTrxName -> saveInTrx(bpartner));
	}

	private void saveInTrx(final BPartner bpartner)
	{
		final I_C_BPartner bpartnerData = bpartner.getBPartnerData();
		InterfaceWrapperHelper.save(bpartnerData, ITrx.TRXNAME_ThreadInherited);

		final int adOrgId = bpartnerData.getAD_Org_ID();

		for (final I_C_BPartner_Location bpLocationData : bpartner.getBPartnerLocations())
		{
			bpLocationData.setC_BPartner(bpartnerData);

			InterfaceWrapperHelper.disableReadOnlyColumnCheck(bpLocationData); // disable it because AD_Org_ID is not updateable
			bpLocationData.setAD_Org_ID(adOrgId); // FRESH-211
			InterfaceWrapperHelper.save(bpLocationData, ITrx.TRXNAME_ThreadInherited);
		}

		for (final I_AD_User contactData : bpartner.getContacts())
		{
			contactData.setC_BPartner(bpartnerData);

			InterfaceWrapperHelper.disableReadOnlyColumnCheck(contactData); // disable it because AD_Org_ID is not updateable
			contactData.setAD_Org_ID(adOrgId); // FRESH-211
			InterfaceWrapperHelper.save(contactData, ITrx.TRXNAME_ThreadInherited);
		}
	}

	private I_C_BPartner retrieveBPartner(final Properties ctx, final int bpartnerId)
	{
		if (bpartnerId <= 0)
		{
			return null;
		}

		// NOTE: use assume caching is enabled for C_BPartner table
		final I_C_BPartner bpartner = InterfaceWrapperHelper.create(ctx, bpartnerId, I_C_BPartner.class, ITrx.TRXNAME_None);

		// Make sure bpartner is from context's AD_Client_ID
		if (bpartner.getAD_Client_ID() != Env.getAD_Client_ID(ctx))
		{
			return null;
		}

		return bpartner;
	}

	@Override
	public <T extends org.compiere.model.I_AD_User> T retrieveDefaultContactOrNull(final I_C_BPartner bpartner, final Class<T> clazz)
	{
		return retrieveContactsAgg(bpartner)
				.getDefaultIfExists(clazz)
				.orElse(null);
	}

	@Override
	public I_AD_User retrieveDefaultContactOrNull(final int bpartnerId)
	{
		final I_AD_User contactUser = retrieveContactsAgg(bpartnerId)
				.getDefaultIfExists()
				.orElse(null);
		if (contactUser == null)
		{
			logger.warn("Every BPartner with associated contacts is expected to have exactly one default contact, but C_BPartner_ID {} doesn't have one.", bpartnerId);
		}
		return contactUser;
	}

	@Override
	public I_AD_User retrieveDefaultContactOrFirstOrNull(final int bpartnerId)
	{
		return retrieveContactsAgg(bpartnerId)
				.getDefaultOrFirst()
				.orElse(null);
	}

	@Override
	public BPartnerLocations retrieveLocations(final I_C_BPartner bpartner)
	{
		Check.assumeNotNull(bpartner, "bpartner not null");
		final Properties ctx = InterfaceWrapperHelper.getCtx(bpartner);
		final int bpartnerId = bpartner.getC_BPartner_ID();
		final String trxName = InterfaceWrapperHelper.getTrxName(bpartner);

		return retrieveLocations(ctx, bpartnerId, trxName);
	}

	@Override
	@Cached(cacheName = I_C_BPartner_Location.Table_Name + "#by#" + I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID)
	public BPartnerLocations retrieveLocations(@CacheCtx final Properties ctx, final int bpartnerId, @CacheTrx final String trxName)
	{
		// TODO: implement copy mechanism when releasing from cache

		final List<I_C_BPartner_Location> bpLocations = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BPartner_Location.class, ctx, trxName)
				.addEqualsFilter(I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.addOnlyActiveRecordsFilter()
				//
				.orderBy()
				.addColumn(I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID)
				.endOrderBy()
				//
				.create()
				.list(I_C_BPartner_Location.class);

		return new BPartnerLocations(bpartnerId, bpLocations);
	}

	@Override
	public List<I_AD_User> retrieveContacts(@CacheCtx final Properties ctx, final int bpartnerId, @CacheTrx final String trxName)
	{
		return retrieveContactsAgg(ctx, bpartnerId, trxName).toList();
	}

	public BPartnerContacts retrieveContactsAgg(final I_C_BPartner bpartner)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(bpartner);
		final String trxName = InterfaceWrapperHelper.getTrxName(bpartner);
		final int bpartnerId = bpartner.getC_BPartner_ID();
		return retrieveContactsAgg(ctx, bpartnerId, trxName);
	}

	public BPartnerContacts retrieveContactsAgg(final int bpartnerId)
	{
		return retrieveContactsAgg(Env.getCtx(), bpartnerId, ITrx.TRXNAME_None);
	}

	@Cached(cacheName = I_AD_User.Table_Name + "#by#" + I_AD_User.COLUMNNAME_C_BPartner_ID)
	public BPartnerContacts retrieveContactsAgg(@CacheCtx final Properties ctx, final int bpartnerId, @CacheTrx final String trxName)
	{
		// TODO: implement copy mechanism when releasing from cache

		final List<I_AD_User> contactsList = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_User.class, ctx, trxName)
				.addEqualsFilter(I_AD_User.COLUMNNAME_C_BPartner_ID, bpartnerId)
				//
				.orderBy()
				.addColumn(org.compiere.model.I_AD_User.COLUMNNAME_AD_User_ID)
				.endOrderBy()
				//
				.create()
				.list(I_AD_User.class);

		return new BPartnerContacts(bpartnerId, contactsList);
	}

	@Override
	public List<I_AD_User> retrieveContacts(final I_C_BPartner bpartner)
	{
		return retrieveContactsAgg(bpartner).toList();
	}

	@Override
	public I_AD_User retrieveContact(
			@CacheCtx final Properties ctx, final int bpartnerId, final boolean isSOTrx, @CacheTrx final String trxName)
	{
		return retrieveContactsAgg(ctx, bpartnerId, trxName)
				.getDefaultSalesOrPurchase(isSOTrx)
				.orElse(null);
	}

	@Override
	public <T extends I_C_BPartner> T retrieveOrgBPartner(
			final Properties ctx, final int orgId, final Class<T> clazz, final String trxName)
	{
		final T result = Services.get(IQueryBL.class).createQueryBuilder(clazz, ctx, trxName)
				.addEqualsFilter(I_C_BPartner.COLUMNNAME_AD_OrgBP_ID, orgId)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(clazz);

		if (result == null)
		{
			throw new OrgHasNoBPartnerLinkException(orgId);
		}
		return result;
	}

	@Override
	public int retrievePricingSystemId(final Properties ctx, final int bPartnerId, final boolean soTrx, final String trxName_IGNORED)
	{
		return retrieveBPartnerAgg(ctx, bPartnerId)
				.getM_PricingSystem_ID(soTrx);
	}

	@Override
	public I_M_DiscountSchema retrieveDiscountSchemaOrNull(final I_C_BPartner bPartner, final boolean soTrx)
	{
		return retrieveBPartnerAgg(Env.getCtx(), bPartner.getC_BPartner_ID())
				.getM_DiscountSchema(soTrx);
	}

	@Override
	public I_M_Shipper retrieveShipper(final int bPartnerId)
	{
		if (bPartnerId <= 0)
		{
			return null;
		}

		return retrieveBPartnerAgg(Env.getCtx(), bPartnerId).getShipperOrDefault();
	}

	@Override
	public I_M_Shipper retrieveDefaultShipper()
	{
		return retrieveDefaultShipper(Env.getCtx());
	}

	@Cached(cacheName = I_M_Shipper.Table_Name + "#by#IsDefault")
	public I_M_Shipper retrieveDefaultShipper(@CacheCtx final Properties ctx)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Shipper.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(de.metas.interfaces.I_M_Shipper.COLUMNNAME_IsDefault, true)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.create()
				.firstOnly(de.metas.interfaces.I_M_Shipper.class);
	}

	@Override
	public I_C_BPartner retrieveBPartnerByValue(final Properties ctx, final String value)
	{
		if (value == null)
		{
			return null;
		}

		final String valueFixed = value.trim();

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BPartner.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_C_BPartner.COLUMNNAME_Value, valueFixed)
				.addOnlyContextClient(ctx)
				.addOnlyActiveRecordsFilter()
				//
				.create()
				.firstOnly(I_C_BPartner.class);
	}

	@Override
	public I_C_BPartner retrieveBPartnerForCacheTrx(final Properties ctx, final int adClientId)
	{
		final I_C_BPartner bpartnerForCashTrx = Services.get(IClientDAO.class)
				.retrieveClientInfo(ctx, adClientId)
				.getC_BPartnerCashTrx();
		if (bpartnerForCashTrx == null)
		{
			logger.error("Not BPartner for CashTrx found for AD_Client_ID={}", adClientId);
		}
		return bpartnerForCashTrx;
	}

	@Override
	public I_C_BP_Relation retrieveBillBPartnerRelationFirstEncountered(final int bpartnerId, final int bpartnerLocationId)
	{
		Preconditions.checkArgument(bpartnerId > 0, "bpartnerId > 0");

		//
		// Filter by location or null (accept bill relations with no location)
		final Integer[] acceptedBPartnerLocationIds;
		if (bpartnerLocationId > 0)
		{
			acceptedBPartnerLocationIds = new Integer[] { bpartnerLocationId, null };
		}
		else
		{
			acceptedBPartnerLocationIds = new Integer[] { null };
		}

		return queryBL.createQueryBuilder(I_C_BP_Relation.class, Env.getCtx(), ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BP_Relation.COLUMN_C_BPartner_ID, bpartnerId)
				.addInArrayOrAllFilter(I_C_BP_Relation.COLUMN_C_BPartner_Location_ID, acceptedBPartnerLocationIds)
				.addEqualsFilter(I_C_BP_Relation.COLUMN_IsBillTo, true)
				//
				.orderBy()
				.addColumn(I_C_BP_Relation.COLUMN_C_BP_Relation_ID) // predictable order
				.endOrderBy()
				//
				.create()
				.first(I_C_BP_Relation.class);
	}

	@Override
	@Cached(cacheName = I_C_BP_Relation.Table_Name + "#by#" + I_C_BP_Relation.COLUMNNAME_C_BPartner_ID + "#" + I_C_BP_Relation.COLUMNNAME_IsBillTo)
	public I_C_BP_Relation retrieveBillToRelation(final int bpartnerId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BP_Relation.class, Env.getCtx(), ITrx.TRXNAME_None)
				.addEqualsFilter(I_C_BP_Relation.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.addEqualsFilter(I_C_BP_Relation.COLUMNNAME_IsBillTo, true)
				.addOnlyActiveRecordsFilter()
				//
				.orderBy()
				.addColumn(I_C_BP_Relation.COLUMNNAME_C_BP_Relation_ID)
				.endOrderBy()
				//
				.create()
				.firstOnly(I_C_BP_Relation.class); // just added an UC
	}

	@Override
	@Cached
	public I_C_BP_Group retrieveDefaultBPGroup(@CacheCtx final Properties ctx)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BP_Group.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_C_BP_Group.COLUMN_IsDefault, true)
				.addOnlyContextClient()
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_C_BP_Group.class);
	}	// get
}
