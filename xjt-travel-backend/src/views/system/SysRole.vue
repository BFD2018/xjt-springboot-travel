<template>
	<div class="xjt-role-view">
		<!--表单渲染-->
		<el-dialog append-to-body :visible.sync="addRoleDialogVisible"
							 :title="Object.keys(roleForm).length>0?'编辑角色':'新增角色'"
							 @close="dialogClose"
							 :close-on-click-modal="false"
							 width="570px">
			<el-form :model="roleForm"
							 ref="ruleForm"
							 label-width="80px"
							 label-position="left"
							 size="small">
				<el-form-item label="用户名" prop="username">
					<el-input v-model="roleForm.name" />
				</el-form-item>
				<el-form-item label="密码" type="password" prop="password">
					<el-input v-model="roleForm.name_zh" />
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button type="primary" v-if="Object.keys(roleForm).length >0" @click="handleInsertRole">提交</el-button>
				<el-button type="primary" v-else @click="handleUpdateRole(userForm)">更新</el-button>
			</div>
		</el-dialog>

		<div class="role-table-list">
			<my-nav style="border-left: 3px solid orange;">
				<div slot="left-slot" style="margin-left: 10px;">角色列表</div>
				<div slot="right-slot" style="margin-right: 10px;">
					<el-button type="success" size="medium" @click="addRoleDialogVisible = true" icon="el-icon-plus">
						新增
					</el-button>
				</div>
			</my-nav>
			<!--表格渲染-->
			<el-table
					:data="roleTableData"
					tooltip-effect="dark"
					style="width: 100%"
					@selection-change="handleSelectionChange">
				<el-table-column
						type="selection"
						width="55">
				</el-table-column>

				<el-table-column prop="name" :show-overflow-tooltip="true" label="角色名称" width="100"></el-table-column>

				<el-table-column :show-overflow-tooltip="true" prop="nameZh" label="角色中文名" />

				<el-table-column
						label="角色权限"
						width="150">
					<template slot-scope="scope">
						{{}}
					</template>
				</el-table-column>

				<el-table-column label="操作" width="150px">
					<template slot-scope="scope">
						<el-button
								size="mini"
								@click="handleEdit(scope.$index, scope.row)">编辑</el-button>
						<el-button
								size="mini"
								type="danger"
								@click="handleDelete(scope.$index, scope.row)">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
		</div>

		<div class="perms-tree-container">
			<my-nav style="border-left: 3px solid orange;">
				<div slot="left-slot" style="margin-left: 10px;">权限树</div>
				<div slot="right-slot" style="margin-right: 10px;">
					<el-button
							:disabled="!showButton"
							icon="el-icon-check"
							type="primary"
							@click="saveMenu">保存</el-button>
				</div>
			</my-nav>
			<div class="perms-tree">
				<el-tree
						:data="menus"
						show-checkbox
						node-key="id"
						:props="defaultProps">
				</el-tree>
			</div>
		</div>
	</div>
</template>

<script>
	import MyNav from "@/components/mynav/MyNav";

	export default {
		name: "SysRole",
		components:{
			MyNav,
		},
		data() {
			return {
				addRoleDialogVisible:false,

				tableLoading:true,
				roleTableData:[],
				multipleSelection: [],
				//用户信息（表单）
				roleForm:{

				},


				showButton:false,
				menus: [{
					id: 1,
					label: '一级 1',
					children: [{
						id: 4,
						label: '二级 1-1',
						children: [{
							id: 9,
							label: '三级 1-1-1'
						}, {
							id: 10,
							label: '三级 1-1-2'
						}]
					}]
				}, {
					id: 2,
					label: '一级 2',
					children: [{
						id: 5,
						label: '二级 2-1'
					}, {
						id: 6,
						label: '二级 2-2'
					}]
				}, {
					id: 3,
					label: '一级 3',
					children: [{
						id: 7,
						label: '二级 3-1'
					}, {
						id: 8,
						label: '二级 3-2'
					}]
				}],
				defaultProps: {
					children: 'children',
					label: 'label'
				}
			}
		},
		methods:{
			/*网络请求*/
			_initTableData(){
				this.$getRequest("/role/all").then(res =>{
					console.log(res);
					if(res.data.status === 200){
						// this.tableUserData.splice(0)
						// this.tableUserData.push(...res.data.obj.records);
						// this.total = Number(res.data.obj.total);

						this.tableLoading = false;
					}else{
						this.$message.warning(res.data.msg);
					}
				})
			},
			/*表格操作*/
			handleEdit(index, row) {
				//编辑用户信息
				console.log(row);
				this.userForm = row;
				this.addRoleDialogVisible = true;
			},
			handleDelete(index, row) {
				console.log(index, row);
				//删除
				// this.$alert('用户删除后不可恢复，请谨慎操作！', '确认删除该用户吗？', {
				// 	confirmButtonText: '确定',
				// 	callback: action => {
				// 		this.$getRequest("/role/delete?id="+row.userId).then(res =>{
				// 			if(res.data.status === 200){
				// 				this.tableUserData.splice(index,1);
				// 				this.$message.success("用户删除成功！")
				// 			}
				// 		})
				// 	}
				// });
			},
			handleSelectionChange(val) {
				this.multipleSelection = val;
			},

			// 保存权限树
			saveMenu() {
				const role = {
					id: this.currentId,
					menus: []
				}
				// 得到已选中的 key 值
				this.menuIds.forEach(function(id) {
					const menu = { id: id }
					role.menus.push(menu)
				})
				this.$getRequest().then((res) => {
					console.log(res);
				}).catch(err => {
					console.log(res.data.msg);
				})
			},

			/*提交新增用户-表单操作*/
			handleInsertRole(){
				this.$postRequest("/user/insert",this.userForm).then(res =>{
					console.log(res);
					if(res.data.status === 200){
						this.$message.success("用户创建成功！")
						this.tableUserData.push(this.userForm);
						this.addUserDialogVisible = false;
						this.userForm = {};
					}
				})
			},
			//更新用户信息
			handleUpdateRole(userObj){
				this.$postRequest("/user/update",userObj).then(res =>{
					console.log(res);
					if(res.data.status === 200){
						this.$message.success("用户更新成功！")
						this.tableUserData.push(this.userForm);
						this.addUserDialogVisible = false;
						this.userForm = {};
					}
				})
			},
			dialogClose(){
				this.roleForm = {};
			},
		},
		created() {
			this._initTableData();
		}
	}
</script>

<style lang="less" scoped>
	.xjt-role-view{
		width: 100%;
		height: 100%;
		padding: 20px;
		display: flex;
		.role-table-list {
			flex: 1;
			box-shadow: 0 3px 10px #ddd;
		}
		.perms-tree-container{
			width: 500px;
			margin-left: 30px;
			box-shadow: 0 3px 10px #ddd;
			border-radius: 8px;
			overflow-x: scroll;
		}
	}
</style>
