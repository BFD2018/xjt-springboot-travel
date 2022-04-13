<template>
	<div class="xjt-netdisk-view">
		<div class="netdisk-left">
			<el-aside width="200px" class="left-aside">
				<el-menu
						:default-active="activeIndex"
						mode="vertical"
						class="el-menu-vertical"
						@select="handleSelect"
						:collapse="isCollapse">
					<el-menu-item>
						<img src="~@/assets/logo.png" class="sidebar-logo">
						<span v-if="!isCollapse" class="sidebar-title">私人网盘</span>
					</el-menu-item>

					<el-menu-item :index="Myconst.ALL">
						<i class="el-icon-menu" style="font-size: 22px"></i>
						<span style="font-size: 20px;margin-left: 8px;">全部</span>
					</el-menu-item>
					<el-menu-item :index="Myconst.IMAGE">
						<i class="el-icon-picture" style="font-size: 22px"></i>
						<span style="font-size: 20px;margin-left: 8px;">图片</span>
					</el-menu-item>
					<el-menu-item :index="Myconst.TEXT">
						<i class="el-icon-document" style="font-size: 22px"></i>
						<span style="font-size: 20px;margin-left: 8px;">文档</span>
					</el-menu-item>
					<el-menu-item :index="Myconst.AUDIO">
						<i class="el-icon-headset" style="font-size: 22px"></i>
						<span style="font-size: 20px;margin-left: 8px;">音频</span>
					</el-menu-item>
					<el-menu-item :index="Myconst.VIDEO">
						<i class="el-icon-film" style="font-size: 22px"></i>
						<span style="font-size: 20px;margin-left: 8px;">视频</span>
					</el-menu-item>
				</el-menu>
			</el-aside>
		</div>

		<div class="netdisk-right">
			<div class="fixed-header">
				<hamburger :is-active="hambuergerIsActive"
									 class="hamburger-container"
									 @toggleClick="toggleSideBar"/>

				<span>首页/面包屑</span>

				<div class="right-menu">
					<header-search id="header-search" @headerSearch="handleHeaderSearch" />
				</div>
			</div>

			<div class="netdisk-main" style="margin-top: 50px">
				<div class="addFile">
					<el-upload
							class="upload-demo"
							drag
							:show-file-list="false"
							:on-success="handleUploadSuccess"
							:action="uploadUrl()">
						<i class="el-icon-upload"></i>
						<div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
					</el-upload>
				</div>

				<el-table
						border
						stripe
						:data="tableData"
						style="width: 90%;margin-top: 20px;">
					<el-table-column
							label="ID"
							width="220">
						<template slot-scope="scope">
							<span>{{ scope.row.id }}</span>
						</template>
					</el-table-column>

					<el-table-column
							label="文件名">
						<template slot-scope="scope">
							<span>{{ scope.row.oldFileName }}</span>
						</template>
					</el-table-column>

					<el-table-column
							label="类型"
							width="220">
						<template slot-scope="scope">
							<span>{{ scope.row.type }}</span>
						</template>
					</el-table-column>

					<el-table-column
							label="文件大小"
							width="120">
						<template slot-scope="scope">
							<span>{{ scope.row.size }}</span>
						</template>
					</el-table-column>

					<el-table-column label="操作" width="240">
						<template slot-scope="scope">
							<el-button size="mini" @click="handlePreviewFileById(scope.row)">预览</el-button>

							<el-button size="mini" type="success" @click="handleDownloadFileById(scope.row)">下载</el-button>

							<el-button class="my-marginLeft10" size="mini" type="danger" @click="handleDeleteFileById(scope.$index, scope.row)">删除</el-button>
						</template>
					</el-table-column>
				</el-table>

				<div class="my-border-padding my-pagination">
					<el-pagination
							@size-change="handleSizeChange"
							@current-change="handleCurrentChange"
							background
							:current-page="currentPage"
							:page-sizes="[10, 20, 30, 40]"
							:page-size="pageSize"
							layout="total, sizes, prev, pager, next, jumper"
							:total="total">
					</el-pagination>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	import HeaderSearch from '@/components/HeaderSearch'
	import Hamburger from '@/components/Hamburger'

	export default {
		name: "NetDisk",
		components: {
			HeaderSearch,
			Hamburger,
		},
		data() {
			return {
				isCollapse: false,				//是否折叠
				activeIndex: this.Myconst.ALL,
				hambuergerIsActive: true,

				tableData: [],
				currentPage: 1,
				pageSize: 6,
				total: 0,

				iframeDialogVisible:false,
				currentPreviewFileUrl:"",
				currentPreviewFileName:"",
			}
		},
		methods: {
			handleSelect(key, keyPath) {
				this.activeIndex = key;
				this._getUserStorageFile();
			},
			toggleSideBar() {
				this.hambuergerIsActive = !this.hambuergerIsActive
				this.isCollapse = !this.isCollapse;
			},
			handleHeaderSearch(val) {
				console.log(val);
			},
			//下载
			handleDownloadFileById(row){
				let fileId = row.id;
				this.$getRequest("/file/download?id="+fileId).then(res =>{
					console.log(res);
				})
			},
			//预览
			handlePreviewFileById(row) {
				let fileId = row.id;
				layer.open({
					type: 2,
					skin: 'layui-layer-demo', //样式类名
					title: '文件预览',
					closeBtn: 1, //显示关闭按钮
					anim: 2,
					area: ['60%', '65%'],
					shadeClose: true, //开启遮罩关闭
					content: "/api/file/preview?id="+fileId
				});
			},
			//删除
			handleDeleteFileById(index,row){
				let rowId = row.id
				layer.confirm('真的删除文件吗？', function () {
					this.$getRequest("/file/delete?id="+fileId).then(res =>{
						layer.msg("删除成功！");
						this.tableData.splice(index,1);
					})

					layer.close(index);
				});
			},
			//上传路径
			uploadUrl(){
				return "/api/file/upload?user_id=" + this.$store.state.login_user.id;
			},
			//上传成功
			handleUploadSuccess(res, file){
				if(res.status === 200){
					this.$message.success("上传成功！")
					this._getUserStorageFile();
				}
			},

			/*获取登录的用户上传的文件*/
			_getUserStorageFile() {
				let params = {
					"current": this.currentPage,
					"pageSize": this.pageSize,
					"type": this.activeIndex,
				}
				this.$getRequest("/file/getByPage?" + this.$qs.stringify(params)).then(res => {
					console.log(res);
					//先清空
					this.tableData.splice(0);
					if(res.data.obj){
						this.tableData = res.data.obj.records;
						this.total = parseInt(res.data.obj.total);
					}
				})
			},
			handleSizeChange(val) {
				this.pageSize = val;
				this.currentPage = 1;
				this._getUserStorageFile();
			},
			handleCurrentChange(val) {
				this.currentPage = val;
				this._getUserStorageFile();
			},
		},
		computed: {

		},
		created() {
			this._getUserStorageFile();
		}
	}
</script>

<style lang="less" scoped>
	.xjt-netdisk-view {
		position: relative;
		height: 100%;
		width: 100%;
		display: flex;

		.netdisk-left {
			width: 200px;

			.left-aside {
				height: 100%;

				.el-menu-vertical {
					height: 100%;

					.sidebar-logo {
						width: 36px;
						height: 36px;
					}

					.sidebar-title {
						margin-left: 10px;
						font-size: 20px;
						font-weight: 600;
						color: #606266;
					}
				}
			}
		}

		.netdisk-right {
			flex: 1;
			position: relative;

			.fixed-header {
				position: absolute;
				top: 0;
				right: 0;
				z-index: 100;
				width: 100%;
				transition: width 0.28s;
				height: 50px;
				line-height: 50px;
				.hamburger-container {
					line-height: 46px;
					height: 100%;
					float: left;
					cursor: pointer;
					transition: background .3s;
					-webkit-tap-highlight-color: transparent;

					&:hover {
						background: rgba(0, 0, 0, .025)
					}
				}
				.right-menu {
					float: right;
					height: 100%;
					line-height: 50px;
				}
			}

			.netdisk-main{
				position: relative;
				padding: 10px;
			}

		}
	}
</style>