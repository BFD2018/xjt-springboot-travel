<template>
	<div class="links-blog-view">
		<!--搜索-->
		<el-row style="margin-top: 10px;margin-bottom: 10px;">
			<el-col :span="8">
				<el-input placeholder="友链搜索" v-model="inputName">
				</el-input>
			</el-col>
			<el-col :span="1">
				<el-button type="primary" @click="searchByInput">
					<i class="el-icon-search"></i>
					搜索
				</el-button>
			</el-col>
		</el-row>

		<!--点击外链地址弹出dialog-iframe展示内容-->
		<el-dialog :title="currentEditLink.blogName" width="70%" :visible.sync="iframeDialogVisible">
			<iframe :src="currentEditLink.blogAddress" frameborder="0" width="100%" height="700px"></iframe>
		</el-dialog>

		<el-table
				:data="tableData"
				border
				style="width: 100%">
			<el-table-column
					label="ID"
					width="220">
				<template slot-scope="scope">
					<span style="margin-left: 10px">{{ scope.row.id }}</span>
				</template>
			</el-table-column>
			<el-table-column
					label="封面图"
					width="300">
				<template slot-scope="scope">
					<el-image
							style="width: 300px; height: 120px"
							:src="scope.row.pictureAddress"
							fit="contain">
					</el-image>
				</template>
			</el-table-column>
			<el-table-column
					label="名称"
					width="450">
				<template slot-scope="scope">
					<span>{{ scope.row.blogName }}</span>
				</template>
			</el-table-column>
			<el-table-column
					label="地址">
				<template slot-scope="scope">
					<div @click="handleOpenLinkInline(scope.row)" class="my-hover1 my-padding">{{scope.row.blogAddress}}</div>
				</template>
			</el-table-column>
		</el-table>

		<!--分页-->
		<div class="pagination my-border-padding">
			<el-pagination
					@size-change="handleSizeChange"
					@current-change="handleCurrentChange"
					:current-page="currentPage"
					:page-sizes="[3, 6, 10, 15]"
					:page-size="pageSize"
					layout="total, sizes, prev, pager, next, jumper"
					:total="total">
			</el-pagination>
		</div>

	</div>
</template>

<script>
	export default {
		name: "LinksBlog",
		components: {},
		data() {
			return {
				inputName:"",
				tableData:[],

				updateLinkDialogFormVisible:false,		//编辑link对话框是否显示
				addLinkDialogFormVisible:false,		//编辑link对话框是否显示
				iframeDialogVisible:false,    //是否显示iframe展示外链内容
				currentEditLink: {
					id:"",
					blogName:"",
					blogAddress:"",
					pictureAddress:"",
				},		//当前编辑的link

				/*form验证规则*/
				linkRules:{
					blogName: [
						{ required: true, message: '请输入友链博客名称', trigger: 'blur' },
					],
					blogAddress: [
						{ required: true, message: '请输入友链博客地址', trigger: 'blur' },
					],
				},

				/*分页*/
				currentPage:1,
				pageSize:6,
				total:0,

			}
		},
		methods: {
			//初始化
			init(){
				this._getAllLinks();
			},
			//网络请求---获取所有友链
			_getAllLinks(){
				this.$getRequest(`/link/getByPage?current=${this.currentPage}&pageSize=${this.pageSize}`).then(res =>{
					console.log(res);
					if(res.data.status === 200){
						//每次请求时清空tableData数据
						this.tableData.splice(0);
						this.tableData.push(...res.data.obj.records)
						this.currentPage = res.data.obj.current;
						this.pageSize = res.data.obj.size;
						this.total = res.data.obj.total;
					}
				})
			},

			/*点击搜索--根据tag.name模糊搜索*/
			searchByInput(){
				if(this.inputNewTagName == ''){
					this.$message.error("请输入tag名")
				}else{
					this.$getRequest("/link/findByName/like?name="+this.inputName).then(res =>{
						console.log(res);
						if(res.data.status === 200){
							this.tableData.splice(0);
							this.tableData.push(...res.data.obj);
						}else{
							this.$message.error(res.data.msg);
						}
					})
				}
			},

			/*打开外链---iframe展示*/
			handleOpenLinkInline(row){
				this.currentEditLink = row;
				this.iframeDialogVisible = true;
			},


			/*分页*/
			handleSizeChange(size){
				/*pageSize 改变时会触发*/
				this.pageSize = size;
				this._getAllLinks();
			},
			handleCurrentChange(current){
				/*currentPage 改变时会触发*/
				this.currentPage = current;
				this._getAllLinks();
			},
		},
		created() {
			this.init();
		}
	}
</script>

<style>

</style>
