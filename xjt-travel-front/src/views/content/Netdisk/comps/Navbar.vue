<template>
	<div class="xjt-navbra-view">
		<hamburger id="hamburger-container"
							 :is-active="hambuergerIsActive"
							 class="hamburger-container"
							 @toggleClick="toggleSideBar"/>

		<span>首页/面包屑</span>

		<div class="right-menu">
			<header-search id="header-search" @headerSearch="handleHeaderSearch" class="right-menu-item"/>
		</div>

	</div>
</template>

<script>
	import HeaderSearch from '@/components/HeaderSearch'
	import Hamburger from '@/components/Hamburger'

	export default {
		name: "Navbar",
		components: {
			Hamburger,
			HeaderSearch
		},
		data() {
			return {
				hambuergerIsActive:true
			}
		},
		methods: {
			toggleSideBar() {
				this.hambuergerIsActive = !this.hambuergerIsActive
			},
			handleHeaderSearch(val){
				console.log(val);
			}
		},
	}
</script>

<style lang="less" scoped>
	.xjt-navbra-view {
		border: 1px solid darkred;
		height: 50px;
		overflow: hidden;
		position: relative;
		background: #fff;
		box-shadow: 0 1px 4px rgba(0, 21, 41, .08);

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

			&:focus {
				outline: none;
			}

			.right-menu-item {
				display: inline-block;
				padding: 0 8px;
				height: 100%;
				font-size: 18px;
				color: #5a5e66;
				vertical-align: text-bottom;

				&.hover-effect {
					cursor: pointer;
					transition: background .3s;

					&:hover {
						background: rgba(0, 0, 0, .025)
					}
				}
			}
		}
	}
</style>