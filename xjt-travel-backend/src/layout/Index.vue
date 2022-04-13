<template>
	<div class="app-wrapper">
		<div class="sidebar-container">
      <sidebar/>
    </div>

		<div class="main-container">
			<div class="navbar">
				<breadcrumb id="breadcrumb-container" class="breadcrumb-container"/>

				<div class="right-menu">
					<el-tooltip content="项目文档" effect="dark" placement="bottom">
						<Doc class="right-menu-item hover-effect"/>
					</el-tooltip>

					<el-dropdown class="avatar-container right-menu-item hover-effect"
											 @command="handleCommand"
											 trigger="click">
						<div class="avatar-wrapper">
							<img v-if="!(Object.keys($store.state.login_user).length >0)" :src="require('@/assets/images/avatar.png')" class="user-avatar">
							<img v-else v-lazy="$store.state.login_user.avatar" class="user-avatar">
							<i class="el-icon-caret-bottom"/>
						</div>
						<el-dropdown-menu slot="dropdown">
							<el-dropdown-item command="a">布局设置</el-dropdown-item>
							<el-dropdown-item command="person">个人中心</el-dropdown-item>
							<el-dropdown-item command="logout" divided>退出登录</el-dropdown-item>
						</el-dropdown-menu>
					</el-dropdown>
				</div>
			</div>

			<transition name="fade-transform" mode="out-in">
				<keep-alive include="TravellerList,TravelNoteList,TravellerArticles,Home,
				RouteList,SysUser,MyEcharts,MyMarkdown,MyWangEditor,MyYamlEdit,MyWordCloud,
				MyTwoLineChart,MyPieChart,">
					<router-view class="main-content"/>
				</keep-alive>
			</transition>
		</div>
	</div>
</template>

<script>
	import Sidebar from './sidebar/Sidebar.vue'
	import Breadcrumb from '@/components/Breadcrumb'
	import Doc from '@/components/Doc'

	export default {
		name: 'Layout',
		components: {
			Sidebar,
			Breadcrumb,
			Doc,
		},
		data(){
			return{
			}
		},
		methods: {
			handleCommand(command){
				if(command === "person"){
					this.$message.success("个人中心")
				}
				if(command === "logout"){
					this.$postRequest("/user/toLogout").then(res =>{
						console.log(res);
						if(res.data.status === 200){
							this.$message.success("注销成功")

							this.$store.commit("UserLogout");
							this.$router.replace("/login");
						}
					})
				}
			},
		}
	}
</script>

<style lang="scss" scoped>
	@import "~@/assets/styles/mixin.scss";
	@import "~@/assets/styles/variables.scss";

	.app-wrapper {
		@include clearfix;
		position: relative;
		height: 100%;
		width: 100%;
    display: flex;

    .sidebar-container{
      width: 220px;
      height: 100%;
    }
    .main-container{
      flex: 1;
			position: relative;
      .navbar {
        height: 50px;
        position: fixed;
        background: #fff;
				width: calc(100% - 200px);
				z-index: 1000;
        box-shadow: 0 2px 3px #ddd;

        .breadcrumb-container {
          float: left;
        }

        .right-menu {
          float: right;
          height: 100%;
          line-height: 50px;
					display: flex;

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

          .avatar-container {
            margin-right: 30px;

            .avatar-wrapper {
              margin-top: 5px;
              position: relative;

              .user-avatar {
                cursor: pointer;
                width: 40px;
                height: 40px;
                border-radius: 10px;
								border: 1px solid #ddd;
              }

              .el-icon-caret-bottom {
                cursor: pointer;
                position: absolute;
                right: -20px;
                top: 25px;
                font-size: 12px;
              }
            }
          }
        }
      }
			.main-content{
				position: relative;
				margin-top: 50px;
			}
    }

	}



</style>
