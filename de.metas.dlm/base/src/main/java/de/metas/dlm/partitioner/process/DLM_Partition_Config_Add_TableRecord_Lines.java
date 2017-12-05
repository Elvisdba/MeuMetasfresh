package de.metas.dlm.partitioner.process;

import java.util.List;
import java.util.stream.Collectors;

import org.adempiere.ad.table.TableRecordIdDescriptor;
import org.adempiere.ad.table.api.ITableRecordIdDAO;
import org.adempiere.util.Services;

import com.google.common.annotations.VisibleForTesting;

import de.metas.dlm.IDLMService;
import de.metas.dlm.model.I_DLM_Partition_Config;
import de.metas.dlm.partitioner.IPartitionerService;
import de.metas.dlm.partitioner.config.PartitionConfig;
import de.metas.process.JavaProcess;
import de.metas.process.Param;

/*
 * #%L
 * metasfresh-dlm
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * For a given config, this process checks its lines and adds additional lines for tables
 * that references the existing lines via <code>*Table_ID</code> and <code>*Record_ID</code> columns.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class DLM_Partition_Config_Add_TableRecord_Lines extends JavaProcess
{

	@Param(parameterName = I_DLM_Partition_Config.COLUMNNAME_DLM_Partition_Config_ID, mandatory = true)
	private I_DLM_Partition_Config configDB;

	private final IPartitionerService partitionerService = Services.get(IPartitionerService.class);

	private final IDLMService dlmService = Services.get(IDLMService.class);

	@Override
	protected String doIt() throws Exception
	{
		final PartitionConfig config = dlmService.loadPartitionConfig(configDB);
		final List<TableRecordIdDescriptor> tableRecordReferences =  Services.get(ITableRecordIdDAO.class).retrieveAllTableRecordIdReferences();

		// get those descriptors whose referencedTableName is the table name of at least one line
		final List<TableRecordIdDescriptor> descriptors = retainRelevantDescriptors(config, tableRecordReferences);

		final PartitionConfig augmentedConfig = partitionerService.augmentPartitionerConfig(config, descriptors);

		dlmService.storePartitionConfig(augmentedConfig);

		return MSG_OK;
	}

	/**
	 * From the given <code>descriptors</code> list, return those ones that reference of the lines of the given <code>config</code>.
	 *
	 * @param config
	 * @param tableRecordReferences
	 * @return
	 */
	@VisibleForTesting
	/* package */ List<TableRecordIdDescriptor> retainRelevantDescriptors(final PartitionConfig config, final List<TableRecordIdDescriptor> descriptors)
	{
		return descriptors.stream()
				.filter(r -> config.getLine(r.getTargetTableName()).isPresent())
				.collect(Collectors.toList());
	}

}
