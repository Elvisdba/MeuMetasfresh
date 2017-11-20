package org.adempiere.mm.attributes.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.X_M_Attribute;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.commons.ProductDescriptor;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class,
		ShutdownListener.class,
		ModelProductDescriptorExtractorUsingAttributeSetInstanceFactory.class })
public class ProductDescriptorFromAttributeSetInstanceFactoryTest
{
	private static final int PRODUCT_ID = 20;
	private ModelProductDescriptorExtractorUsingAttributeSetInstanceFactory factory;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		factory = new ModelProductDescriptorExtractorUsingAttributeSetInstanceFactory();
	}

	@Test
	public void productDescriptorFactory_bean_is_a_ProductDescriptorFromAttributeSetInstanceFactory()
	{
		final ModelProductDescriptorExtractor productDescriptor = Adempiere.getBean(ModelProductDescriptorExtractor.class);
		assertThat(productDescriptor).isInstanceOf(ModelProductDescriptorExtractorUsingAttributeSetInstanceFactory.class);
	}

	@Test
	public void createProductDescriptor_PP_Order_no_ASI()
	{
		final I_PP_Order ppOrder = newInstance(I_PP_Order.class);
		ppOrder.setM_Product_ID(20);

		final ProductDescriptor productDescriptor = factory.createProductDescriptor(ppOrder);
		assertThat(productDescriptor).isNotNull();
		assertThat(productDescriptor.getProductId()).isEqualTo(20);
		assertThat(productDescriptor.getAttributeSetInstanceId()).isEqualTo(AttributeConstants.M_AttributeSetInstance_ID_None);
		assertThat(productDescriptor.getStorageAttributesKey()).isEqualTo(ProductDescriptor.STORAGE_ATTRIBUTES_KEY_UNSPECIFIED);
	}

	@Test
	public void createProductDescriptor_PP_Order_with_ASI()
	{
		final I_M_AttributeSetInstance asi = createASI();

		final I_PP_Order ppOrder = newInstance(I_PP_Order.class);
		ppOrder.setM_Product_ID(PRODUCT_ID);
		ppOrder.setM_AttributeSetInstance_ID(asi.getM_AttributeSetInstance_ID());

		final ProductDescriptor productDescriptor = factory.createProductDescriptor(ppOrder);

		assertProductDescriptorMatchesProductAndASI(productDescriptor, asi);
	}

	@Test
	public void createProductDescriptor_PP_Order_BOMLine_with_ASI()
	{
		final I_M_AttributeSetInstance asi = createASI();

		final I_PP_Order_BOMLine ppOrderBomLine = newInstance(I_PP_Order_BOMLine.class);
		ppOrderBomLine.setM_Product_ID(PRODUCT_ID);
		ppOrderBomLine.setM_AttributeSetInstance_ID(asi.getM_AttributeSetInstance_ID());

		final ProductDescriptor productDescriptor = factory.createProductDescriptor(ppOrderBomLine);

		assertProductDescriptorMatchesProductAndASI(productDescriptor, asi);
	}


	@Test
	public void createProductDescriptor_DD_OrderLine_with_ASI()
	{
		final I_M_AttributeSetInstance asi = createASI();

		final I_DD_OrderLine ddOrderLine = newInstance(I_DD_OrderLine.class);
		ddOrderLine.setM_Product_ID(PRODUCT_ID);
		ddOrderLine.setM_AttributeSetInstance_ID(asi.getM_AttributeSetInstance_ID());

		final ProductDescriptor productDescriptor = factory.createProductDescriptor(ddOrderLine);

		assertProductDescriptorMatchesProductAndASI(productDescriptor, asi);
	}

	public void assertProductDescriptorMatchesProductAndASI(final ProductDescriptor productDescriptor, final I_M_AttributeSetInstance asi)
	{
		assertThat(productDescriptor).isNotNull();
		assertThat(productDescriptor.getProductId()).isEqualTo(20);
		assertThat(productDescriptor.getAttributeSetInstanceId()).isEqualTo(asi.getM_AttributeSetInstance_ID());
		assertThat(productDescriptor.getStorageAttributesKey()).isEqualTo("value1" + ProductDescriptor.STORAGE_ATTRIBUTES_KEY_DELIMITER + "value3");
	}



	public I_M_AttributeSetInstance createASI()
	{
		final AttributesTestHelper attributesTestHelper = new AttributesTestHelper();
		final I_M_Attribute attribut1 = attributesTestHelper.createM_Attribute("testAttrib1", X_M_Attribute.ATTRIBUTEVALUETYPE_List, true);
		attribut1.setIsStorageRelevant(true);
		save(attribut1);

		final I_M_Attribute attribut2 = attributesTestHelper.createM_Attribute("testAttrib2", X_M_Attribute.ATTRIBUTEVALUETYPE_List, true);
		attribut2.setIsStorageRelevant(false);
		save(attribut2);

		final I_M_Attribute attribut3 = attributesTestHelper.createM_Attribute("testAttrib3", X_M_Attribute.ATTRIBUTEVALUETYPE_List, true);
		attribut3.setIsStorageRelevant(true);
		save(attribut3);

		final I_M_AttributeSetInstance asi = newInstance(I_M_AttributeSetInstance.class);
		save(asi);

		final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
		final I_M_AttributeInstance ai1 = attributeSetInstanceBL.getCreateAttributeInstance(asi, attribut1.getM_Attribute_ID());
		ai1.setValue("value1");
		save(ai1);
		final I_M_AttributeInstance ai2 = attributeSetInstanceBL.getCreateAttributeInstance(asi, attribut2.getM_Attribute_ID());
		ai2.setValue("value2");
		save(ai2);
		final I_M_AttributeInstance ai3 = attributeSetInstanceBL.getCreateAttributeInstance(asi, attribut3.getM_Attribute_ID());
		ai3.setValue("value3");
		save(ai3);

		return asi;
	}
}
