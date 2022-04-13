<template>
	<div class="app-container">
		<!--表格渲染-->
		<el-table ref="table"
							:data="tableData"
							style="width: 100%;"
							@selection-change="selectionChangeHandler">
			<el-table-column type="selection" width="55"/>
			<el-table-column :show-overflow-tooltip="true" prop="tableName" label="文件名"/>
			<el-table-column :show-overflow-tooltip="true" prop="engine" label="后缀名"/>

			<el-table-column label="操作" width="160px" align="center" fixed="right">
				<template slot-scope="scope">
					<el-button size="mini" style="margin-right: 2px" type="text">
						<router-link :to="'/sys-tools/generator/preview/' + scope.row.tableName">预览</router-link>
					</el-button>
					<el-button size="mini" style="margin-left: -1px;margin-right: 2px" type="text"
										 @click="toDownload(scope.row.tableName)">下载
					</el-button>
				</template>
			</el-table-column>
		</el-table>

		<!--分页-->
		<div class="my-pagination">
			<el-pagination
					background
					@size-change="handleSizeChange"
					@current-change="handleCurrentChange"
					:current-page="currentPage"
					:page-sizes="[3,6,9,12]"
					:page-size="pageSize"
					layout="total, sizes, prev, pager, next, jumper"
					:total="total">
			</el-pagination>
		</div>
	</div>
</template>

<script>
	import {downloadFile} from '@/utils/index'

	export default {
		name: 'GeneratorIndex',
		data() {
			return {
				tableData: [],

				/*分页*/
				currentPage: 1,
				pageSize: 6,
				total: 0
			}
		},
		methods: {
			selectionChangeHandler() {

			},
			toDownload(tableName, type = 2) {
				this.$getRequest({
					url: '/generator/' + tableName + '/' + type,
					method: 'post',
					responseType: type === 2 ? 'blob' : ''
				}).then(res => {
					downloadFile(res, tableName, 'zip');
				})
			},

			/*分页*/
			handleSizeChange(val) {
				this.pageSize = val;
				this.initTableData();
			},
			handleCurrentChange(val) {
				this.currentPage = val;
				this.initTableData();
			},
		},
	}
</script>

<style scoped>

</style>
