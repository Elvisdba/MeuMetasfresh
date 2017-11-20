package de.metas.material.planning.pporder;

import org.eevolution.model.I_PP_Order_BOMLine;
import org.springframework.stereotype.Service;

import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderLine;
import de.metas.material.event.pporder.ProductionRequestedEvent;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-material-planning
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
@Service
public class PPOrderPojoConverter
{
	private final ModelProductDescriptorExtractor productDescriptorFactory;

	public PPOrderPojoConverter(@NonNull final ModelProductDescriptorExtractor productDescriptorFactory)
	{
		this.productDescriptorFactory = productDescriptorFactory;
	}

	public PPOrderLine asPPOrderLinePojo(@NonNull final I_PP_Order_BOMLine ppOrderBOMLine)
	{
		final ProductDescriptor productDescriptor = productDescriptorFactory.createProductDescriptor(ppOrderBOMLine);

		return PPOrderLine.builder()
				.productBomLineId(ppOrderBOMLine.getPP_Product_BOMLine_ID())
				.productDescriptor(productDescriptor)
				.description(ppOrderBOMLine.getDescription())
				.qtyRequired(ppOrderBOMLine.getQtyRequiered())
				.build();
	}

	public PPOrder asPPOrderPojo(@NonNull final ProductionRequestedEvent event)
	{
		return event.getPpOrder();
	}

}
