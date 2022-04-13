<template>
	<div class="login-view">
		<el-form ref="loginForm" :model="loginForm"
						 :rules="loginRules"
             label-position="left" label-width="0px"
						 class="login-form">
			<h3 class="title">星光旅游后台登录</h3>
			<el-form-item prop="username">
				<el-input v-model="loginForm.username" type="text" placeholder="账号">
					<svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon"/>
				</el-input>
			</el-form-item>

			<el-form-item prop="password">
				<el-input v-model="loginForm.password" type="password" placeholder="密码"
									@keyup.enter.native="handleLogin">
					<svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon"/>
				</el-input>
			</el-form-item>

			<el-form-item prop="code">
				<el-input v-model="loginForm.verify_code" placeholder="验证码" style="width: 63%"
									@keyup.enter.native="handleLogin">
					<svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon"/>
				</el-input>
				<div class="login-code">
					<img src="/api/user/getCaptcha" id="verify_code" @click="getRefreshCode">
				</div>
			</el-form-item>

			<el-checkbox v-model="loginForm.rememberMe" style="margin:0 0 25px 0;">
				记住我
			</el-checkbox>

			<el-form-item style="width:100%;">
				<el-button size="medium" type="primary" style="width:100%;" @click.native.prevent="handleLogin">登 录</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>

<script>
	export default {
		name: 'Login',
		data() {
			return {
				loginForm: {
					username: 'root',
					password: '123',
					rememberMe: false,
					verify_code:"",
				},
				loginRules: {
					username: [{required: true, trigger: 'blur', message: '用户名不能为空'}],
					password: [{required: true, trigger: 'blur', message: '密码不能为空'}],
					verify_code: [{required: true, trigger: 'change', message: '验证码不能为空'}]
				},
			}
		},
		watch: {
			$route: {
				handler: function (route) {
					this.redirect = route.query && route.query.redirect
				},
				immediate: true
			}
		},
		created() {
			console.log(this.$route);
		},
		methods: {
			getRefreshCode() {
				console.log(this);
        this.$notify.success("更新验证码");
        document.getElementById("verify_code").setAttribute("src","/api/user/getCaptcha?time=" + new Date());
			},

			handleLogin() {
				this.$postRequest("/user/toLogin",this.loginForm).then(res =>{
					console.log(res);
					if(res.data.status === 200){
						this.$store.commit("updateLoginUser",res.data.obj);

						this.$router.push("/dashboard");
					}else{
					  this.$message.error(res.data.msg)
          }
				})
			},
		}
	}
</script>

<style rel="stylesheet/scss" lang="scss">
	.login-view {
		display: flex;
		justify-content: center;
		align-items: center;
		height: 100vh;
		background-image:url("~@/assets/images/background.jpg");
		background-size:100% 100%;
		background-repeat:no-repeat;
	}

	.title {
		margin: 0 auto 30px auto;
		text-align: center;
		color: #707070;
	}

	.login-form {
		border-radius: 6px;
		background: #ffffff;
		width: 385px;
		padding: 25px 25px 5px 25px;

		.el-input {
			height: 38px;

			input {
				height: 38px;
			}
		}

		.input-icon {
			height: 39px;
			width: 14px;
			margin-left: 2px;
		}
	}

	.login-code {
		width: 33%;
		display: inline-block;
		height: 38px;
		float: right;

		img {
			cursor: pointer;
			vertical-align: middle
		}
	}
</style>
