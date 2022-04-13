<template>
	<div>
		<sidebar-logo v-if="true"/>

		<el-scrollbar wrap-class="scrollbar-wrapper">
			<el-menu
					:default-active="sidebarActiveIndex"
					router
					unique-opened
					:background-color="variables.menuBg"
					:text-color="variables.menuText"
					:active-text-color="variables.menuActiveText"
					:collapse-transition="false"
					mode="vertical">
				<el-menu-item index="/dashboard">
					<svg-icon icon-class="dashboard" class-name="dashboard-icon"/>
					首页</el-menu-item>

				<!--游记管理-->
				<el-submenu index="/travel">
					<template slot="title">
						<svg-icon icon-class="note" class-name="note-icon"/>
						<span slot="title">游记管理</span>
					</template>
					<el-menu-item index="/travel/notelist">
						<svg-icon icon-class="list" />
						旅行游记
					</el-menu-item>
					<el-menu-item index="/travel/write">
						<svg-icon icon-class="plus" />
						新增游记</el-menu-item>
				</el-submenu>

				<!--系统管理-->
				<el-submenu index="/system">
					<template slot="title">
						<svg-icon icon-class="setting" class-name="setting-icon"/>
						<span slot="title">系统管理</span>
					</template>
					<el-menu-item index="/system/user">
						<svg-icon icon-class="peoples" />
						用户管理
					</el-menu-item>
					<el-menu-item index="/system/role_perm">
						<svg-icon icon-class="role" />
						角色&权限</el-menu-item>
					<el-menu-item index="/system/job">
						<svg-icon icon-class="Steve-Jobs" />
						岗位管理</el-menu-item>
				</el-submenu>

				<!--旅行家管理-->
				<el-submenu index="/traveller">
					<template slot="title">
						<svg-icon icon-class="traveller" class-name="traveller-icon"/>
						<span slot="title">旅行家管理</span>
					</template>
					<el-menu-item index="/traveller/list">
						<svg-icon icon-class="peoples" />
						旅行家列表
					</el-menu-item>
					<el-menu-item index="/traveller/add">
						<svg-icon icon-class="edit" />
						新增旅行家
					</el-menu-item>
					<el-menu-item index="/traveller/article/list">
						<svg-icon icon-class="education" />
						旅行家文章列表
					</el-menu-item>
					<el-menu-item index="/traveller/article/add">
						<svg-icon icon-class="plus" />
						新增旅行家文章
					</el-menu-item>
				</el-submenu>

				<!--路线管理-->
				<el-submenu index="/trips">
					<template slot="title">
						<svg-icon icon-class="route" class-name="route-icon"/>
						<span slot="title">路线管理</span>
					</template>
					<el-menu-item index="/trips/list">
						<svg-icon icon-class="list" />
						路线列表
					</el-menu-item>
					<el-menu-item index="/trips/add">
						<i class="el-icon-plus" />
						新增路线
					</el-menu-item>
					<el-menu-item index="/seller/list">
						<svg-icon icon-class="tree-table" />
						旅行社
					</el-menu-item>
				</el-submenu>

				<!--系统工具-->
				<el-submenu index="/sys-tools">
					<template slot="title">
						<svg-icon icon-class="sys-tools" />
						<span slot="title">系统工具</span>
					</template>
					<el-menu-item index="/sys-tools/generator">
						<svg-icon icon-class="dev" />
						代码生成
					</el-menu-item>
					<el-menu-item index="/sys-tools/storage">
						<svg-icon icon-class="qiniu" />
						存储管理
					</el-menu-item>
          <el-menu-item index="/sys-tools/preview">
            <i class="el-icon-view" />
            文件预览
          </el-menu-item>
				</el-submenu>

				<!--组件管理-->
				<el-submenu index="/components">
					<template slot="title">
						<svg-icon icon-class="zujian" />
						<span slot="title">组件管理</span>
					</template>
					<el-menu-item index="/components/echarts">
						<svg-icon icon-class="chart" />
						图表库
					</el-menu-item>
					<el-menu-item index="/components/icon">
						<svg-icon icon-class="icon" />
						图标库
					</el-menu-item>
					<el-menu-item index="/components/wangEditor">
						<svg-icon icon-class="fwb" />
						富文本
					</el-menu-item>
					<el-menu-item index="/components/markdown">
						<svg-icon icon-class="markdown" />
						Markdown
					</el-menu-item>
					<el-menu-item index="/components/yaml">
						<svg-icon icon-class="dev" />
						Yaml编辑器
					</el-menu-item>
				</el-submenu>

				<el-menu-item index="/about">
					<svg-icon icon-class="about" class-name="about-icon"/>
					关于我</el-menu-item>
			</el-menu>
		</el-scrollbar>
	</div>
</template>

<script>
	import SidebarLogo from './SidebarLogo'
	import variables from '@/assets/styles/variables.scss'

	export default {
		components: {
			SidebarLogo
		},
    data(){
		  return{
        sidebarActiveIndex:"/dashboard"
      }
    },
		computed: {
			activeMenu() {
				const route = this.$route
				const { meta, path } = route
				// if set path, the sidebar will highlight the path you set
				if (meta.activeMenu) {
					return meta.activeMenu
				}
				return path
			},
			variables() {
				return variables
			},
		},
    watch:{
      $route(route){
        this.sidebarActiveIndex = route.path;
      }
    },
	}
</script>
