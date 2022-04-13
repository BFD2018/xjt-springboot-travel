<template>
	<div class="sidebar-logo-container">
		<router-link key="collapse" class="sidebar-logo-link" to="/">
			<img v-if="logo" :src="logo" class="sidebar-logo">
			<h1 class="sidebar-title">{{ title }} </h1>
		</router-link>
	</div>
</template>

<script>
	import Logo from '@/assets/images/logo.png'

	export default {
		name: 'SidebarLogo',
		data() {
			return {
				title: 'ELADMIN-后台管理',
				logo: Logo
			}
		}
	}
</script>

<style lang="scss" scoped>
	.sidebar-logo-container {
		position: relative;
		width: 100%;
		height: 50px;
		line-height: 50px;
		text-align: center;
		overflow: hidden;

		& .sidebar-logo-link {
			height: 100%;
			width: 100%;

			& .sidebar-logo {
				width: 32px;
				height: 32px;
				vertical-align: middle;
				margin-right: 6px;
			}

			& .sidebar-title {
				display: inline-block;
				margin: 0;
				color: #fff;
				font-weight: 600;
				line-height: 50px;
				font-size: 14px;
				font-family: Avenir, Helvetica Neue, Arial, Helvetica, sans-serif;
				vertical-align: middle;
			}
		}

		&.collapse {
			.sidebar-logo {
				margin-right: 0px;
			}
		}
	}
</style>
