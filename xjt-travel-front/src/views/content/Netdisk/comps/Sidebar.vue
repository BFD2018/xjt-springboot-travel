<template>
	<div class="xjt-sidebar-view">
		<div class="sidebar-logo-container" :class="{'collapse':isCollapse}">
			<transition name="sidebarLogoFade">
				<router-link v-if="isCollapse" key="collapse" class="sidebar-logo-link" to="/">
					<img v-if="logo" :src="logo" class="sidebar-logo">
					<span v-else class="sidebar-title">{{ title }} </span>
				</router-link>
				<router-link v-else key="expand" class="sidebar-logo-link" to="/">
					<img v-if="logo" :src="logo" class="sidebar-logo">
					<span class="sidebar-title">{{ title }} </span>
				</router-link>
			</transition>
		</div>

		<el-aside width="200px">
			<el-menu :default-active="activeMenu()"
							 mode="vertical"
							 router
							 class="el-menu-vertical-demo"
							 :collapse="isCollapse">
				<el-menu-item index="/netdist/all">
					<i class="el-icon-menu"></i>
					<span slot="title">全部</span>
				</el-menu-item>
				<el-menu-item index="/netdist/picture">
					<i class="el-icon-picture"></i>
					<span slot="title">图片</span>
				</el-menu-item>
				<el-menu-item index="/netdist/doc">
					<i class="el-icon-document"></i>
					<span slot="title">文档</span>
				</el-menu-item>
				<el-menu-item index="/netdist/music">
					<i class="el-icon-headset"></i>
					<span slot="title">音乐</span>
				</el-menu-item>
				<el-menu-item index="/netdist/video">
					<i class="el-icon-film"></i>
					<span slot="title">视频</span>
				</el-menu-item>
			</el-menu>
		</el-aside>
	</div>
</template>

<script>
	import Logo from '@/assets/logo.png'

	export default {
		name: "Sidebar",
		props:{
			isCollapse:{
				type:Boolean,
				default:true
			}
		},
		data(){
			return{
				title: '私人网盘',
				logo: Logo,
			}
		},
		methods:{
			activeMenu() {
				console.log(this.$route);

				const { meta, path } = this.$route

				return path
			},
		},
	}
</script>

<style lang="less" scoped>
	.xjt-sidebar-view{
		width: 200px;
		height: 100%;
		.sidebar-logo{
			width: 40px;
			height: 40px;
		}
	}
</style>