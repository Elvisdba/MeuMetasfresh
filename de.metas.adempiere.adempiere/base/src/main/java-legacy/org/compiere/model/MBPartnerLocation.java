package org.compiere.model;

import java.sql.ResultSet;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.IBPartnerDAO;

/**
 * BPartner location
 * 
 * @author metas-dev <dev@metasfresh.com>
 * @author based on initial work of Jorg Janke
 */
@SuppressWarnings("serial")
public class MBPartnerLocation extends X_C_BPartner_Location
{
	public MBPartnerLocation(final Properties ctx, final int C_BPartner_Location_ID, final String trxName)
	{
		super(ctx, C_BPartner_Location_ID, trxName);
		if (C_BPartner_Location_ID == 0)
		{
			setName(".");
			//
			setIsShipTo(true);
			setIsRemitTo(true);
			setIsPayFrom(true);
			setIsBillTo(true);
		}
	}

	public MBPartnerLocation(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("MBPartner_Location[ID=")
				.append(get_ID())
				.append(",C_Location_ID=").append(getC_Location_ID())
				.append(",Name=").append(getName())
				.append("]");
		return sb.toString();
	}

	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		if (getC_Location_ID() <= 0)
		{
			throw new FillMandatoryException(COLUMNNAME_C_Location_ID);
		}

		// Set New Name
		if (newRecord)
		{
			final String name = new UniqueNameBuilder(this).build();
			setName(name);
		}

		return true;
	}

	private static final class UniqueNameBuilder
	{
		private final I_C_BPartner_Location bpLocation;

		/** Unique Name */
		private String m_uniqueName = null;
		private int m_unique = 0;

		private UniqueNameBuilder(final I_C_BPartner_Location bpLocation)
		{
			this.bpLocation = bpLocation;
		}

		public String build()
		{
			final I_C_Location address = getC_Location();
			m_uniqueName = getBPLocationName();
			if (m_uniqueName != null && m_uniqueName.equals("."))
			{
				m_uniqueName = null;
			}
			m_unique = 0;

			// metas: if possible, we want to preserve the location name as it is
			if (Check.isEmpty(m_uniqueName, true))
			{
				makeUnique(address);
			}

			// Check uniqueness
			final List<I_C_BPartner_Location> allBPLocations = retrieveAllBPartnerLocations();
			boolean unique = allBPLocations.isEmpty();

			// metas: this code can hang if not enough location fields are set
			while (!unique)
			{
				unique = true;
				for (final I_C_BPartner_Location bpLocation : allBPLocations)
				{
					if (bpLocation.getC_BPartner_Location_ID() == getC_BPartner_Location_ID())
					{
						continue;
					}
					if (m_uniqueName.equals(bpLocation.getName()))
					{
						makeUnique(address);
						unique = false;
						break;
					}
				}
			}

			return m_uniqueName;
		}

		private int getC_BPartner_Location_ID()
		{
			return bpLocation.getC_BPartner_Location_ID();
		}

		private I_C_Location getC_Location()
		{
			return bpLocation.getC_Location();
		}

		private String getBPLocationName()
		{
			return bpLocation.getName();
		}

		private List<I_C_BPartner_Location> retrieveAllBPartnerLocations()
		{
			final Comparator<I_C_BPartner_Location> ordering = Comparator.<I_C_BPartner_Location, Integer> //
					comparing(bpl -> bpl.isActive() ? 0 : 1)
					.thenComparing(bpl -> bpl.isShipTo() ? 0 : 1) // metas: task 03537 cg: shipTo has priority
					.thenComparing(bpl -> bpl.isBillTo() ? 0 : 1)
					.thenComparing(bpl -> bpl.getC_BPartner_Location_ID());

			final Properties ctx = InterfaceWrapperHelper.getCtx(bpLocation);
			final String trxName = InterfaceWrapperHelper.getTrxName(bpLocation);
			return Services.get(IBPartnerDAO.class)
					.retrieveBPartnerLocations(ctx, bpLocation.getC_BPartner_ID(), trxName)
					.stream()
					.sorted(ordering)
					.collect(ImmutableList.toImmutableList());
		}

		/**
		 * Make name Unique
		 *
		 * @param address address
		 */
		private void makeUnique(final I_C_Location address)
		{

			if (m_uniqueName == null)
			{
				m_uniqueName = "";
			}
			m_unique++;

			// 0 - City
			if (m_uniqueName.length() == 0)
			{
				final String xx = address.getCity();
				if (xx != null && xx.length() > 0)
				{
					m_uniqueName = xx;
				}
				m_unique = 1;
			}
			// 1 + Address1
			if (m_unique == 1 || m_uniqueName.length() == 0)
			{
				final String xx = address.getAddress1();
				if (xx != null && xx.length() > 0)
				{
					if (m_uniqueName.length() > 0)
					{
						m_uniqueName += " ";
					}
					m_uniqueName += xx;
				}
				m_unique = 2;
			}
			// 2 + Address2
			if (m_unique == 2 || m_uniqueName.length() == 0)
			{
				final String xx = address.getAddress2();
				if (xx != null && xx.length() > 0)
				{
					if (m_uniqueName.length() > 0)
					{
						m_uniqueName += " ";
					}
					m_uniqueName += xx;
				}
				m_unique = 3;
			}
			// 3 + Address3
			if (m_unique == 3 || m_uniqueName.length() == 0)
			{
				final String xx = address.getAddress3();
				if (xx != null && xx.length() > 0)
				{
					if (m_uniqueName.length() > 0)
					{
						m_uniqueName += " ";
					}
					m_uniqueName += xx;
				}
				m_unique = 4;
			}
			// 4 + Address4
			if (m_unique == 4 || m_uniqueName.length() == 0)
			{
				final String xx = address.getAddress4();
				if (xx != null && xx.length() > 0)
				{
					if (m_uniqueName.length() > 0)
					{
						m_uniqueName += " ";
					}
					m_uniqueName += xx;
				}
				m_unique = 4;
			}
			// 5 - Region
			if (m_unique == 5 || m_uniqueName.length() == 0)
			{
				final I_C_Region region = address.getC_Region();
				final String regionName = region == null ? null : region.getName();
				if (!Check.isEmpty(regionName, true))
				{
					if (m_uniqueName.length() > 0)
					{
						m_uniqueName += " ";
					}
					m_uniqueName += regionName;
				}
				m_unique = 5;
			}
			// 6 - ID
			if (m_unique == 6 || m_uniqueName.length() == 0)
			{
				int id = getC_BPartner_Location_ID();
				if (id <= 0)
				{
					id = address.getC_Location_ID();
				}
				m_uniqueName += " #" + id;
				m_unique = 6;
			}
			// metas: we need to make sure this will return a unique result anyway and not end in an infinite loop
			if (m_unique > 6)
			{
				m_uniqueName += "-" + (m_unique - 6);
			}
		}	// makeUnique

	}
}	// MBPartnerLocation
