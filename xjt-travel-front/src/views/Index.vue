<template>
  <div class="index-view">
    <div id="header-outer">
      <div style="display: flex;margin: auto;width: 90%;">
        <div style="width: 200px;height: 100%;">
          <el-image
            style="width: 180px; height: 60px;line-height: 60px;"
            :src="require('@/assets/images/common/xingguang-travel.jpg')"
            fit="fill"></el-image>
        </div>

        <div class="head-nav" style="flex: 3;">
          <el-menu
            :default-active="navActiveIndex"
            router
            class="el-menu-demo"
            mode="horizontal"
            active-text-color="#ffd04b">
            <el-menu-item index="/home">首页</el-menu-item>
            <el-menu-item index="/traveller">旅行家专栏</el-menu-item>
            <el-menu-item index="/guides">旅游攻略</el-menu-item>
            <el-menu-item index="/trips">去旅行</el-menu-item>
            <el-menu-item index="/bbs">社区</el-menu-item>
            <el-menu-item :index="gotoCartPage()">购物车</el-menu-item>
            <el-menu-item index="/about">关于我</el-menu-item>
          </el-menu>
        </div>

        <div style="flex: 1;line-height: 60px;">
          <el-input
            size="medium "
            placeholder="请输入内容"
            @change="handleIndexInputSearch"
            v-model="inputSearchKey">
            <el-button slot="append" type="success" icon="el-icon-search"></el-button>
          </el-input>
        </div>

        <div v-if="$store.state.login_user" style="float: right;line-height: 60px;margin-left: 30px;">
          <el-dropdown class="userinfo-dropdown" @command="handleCommand">
								<span class="el-dropdown-link">
									<el-image
                    style="width: 55px; height: 55px;border-radius:50%"
                    :src="$store.state.login_user.avatar|filterImgUrl(that)"
                    fit="cover">
									</el-image>
								</span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="adminManagement"><i class="el-icon-s-platform"></i>后台管理</el-dropdown-item>
              <el-dropdown-item command="userInfo"><i class="el-icon-user"></i>个人中心</el-dropdown-item>
              <el-dropdown-item command="cart"><i class="el-icon-shopping-cart-1"></i>购物车</el-dropdown-item>
              <el-dropdown-item command="setting"><i class="el-icon-setting"></i>设置</el-dropdown-item>
              <el-dropdown-item command="logout"><i class="el-icon-s-flag"></i>注销登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
        <div class="btns" style="float: right;line-height: 60px;" v-else>
          <el-button type="success" size="medium" @click="$router.push('/login')">登录</el-button>
          <span style="margin-left: 20px"></span>
          <el-button type="warning" size="medium" @click="$router.push('/register')">注册</el-button>
        </div>

      </div>
    </div>

    <div class="middle-container">
      <router-view :inputSearchKey="inputSearchKey" ref="childComp" :key="routerViewKey"/>
    </div>

    <div id="footer-outer">
      <div class="footer-content">
        <div class="item contact-me">
          <h3>联系我</h3>
          <p>Email:1351655382@qq.com</p>
          <p>Phone:15990076961</p>
        </div>
        <div class="item focus-me">
          <h3>微信</h3>
          <el-image
            style="width: 100px; height: 100px"
            :src="require('@/assets/images/common/wx.png')"
            fit="contain"></el-image>
        </div>
        <div class="item talk-us">
          <h3>支付宝</h3>
          <el-image
            style="width: 100px; height: 100px"
            :src="require('@/assets/images/common/zhifubao.png')"
            fit="contain"></el-image>
        </div>
        <div class="item website-info">
          <h3>站点信息</h3>
          <p><b>文章总数：</b>16篇</p>
          <p><b>访问总数：</b>174次</p>
          <p><b>评论总数：</b>7条</p>
          <p><b>留言总数：</b>19条</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "Index",
    data() {
      return {
        inputSearchKey: '',
        navActiveIndex:"/home",
      }
    },
    computed: {
      routerViewKey() {
        return this.$route.name !== undefined ? this.$route.name + +new Date() : this.$route + +new Date()
      }
    },
    methods: {
      blogTypeMore() {
        console.log("加载更多");
      },
      handleIndexInputSearch(val) {
        console.log(val);
        this.inputSearchKey = val;
        this.$refs.childComp.initTravelNoteList();
      },
      handleCommand(command) {
        this.$message.success(command);
        if (command === "adminManagement") {
          window.location.href = "http://localhost:8001/";
        }
        if (command === "userInfo") {
          this.$router.push("/user/info/")
        }
        if (command === "cart") {
          this.$router.push("/cart/"+this.$store.state.login_user.userId)
        }
        if (command === "setting") {
          this.$router.push("/user/setting/")
        }
        if (command === "logout") {
          this.$getRequest("/user/toLogout").then(res => {
            this.$store.commit("removeLoginUser");
            this.$message.success("退出成功！");
            this.$router.push("/login");
          })
        }
      },

      gotoCartPage(){
        return "/cart/" + this.$store.state.login_user.userId
      }
    },
    watch:{
      $route(route){
        this.navActiveIndex = route.path;
      }
    },
    created() {
      if(!this.$store.state.login_user){
        this.$router.push("/login")
      }
    },
  }
</script>

<style lang="less" scoped>
  .index-view {
    position: relative;
    width: 100vw;
    height: auto;

    #header-outer {
      width: 100%;
      height: 60px;
      background-color: white;
      position: fixed;
      top: 0;
      left: 0;
      z-index: 100;
      box-shadow: 0 2px 3px #ddd;

      .head-nav {
        .el-menu-item {
          font-size: 20px;
        }
      }

    }
  }


  .middle-container {
    width: 90vw;
    min-height: 800px;
    margin: 80px auto 20px auto;
    /*border: 1px solid red;*/
  }

  #footer-outer {
    background-color: rgb(84, 92, 100);

    .footer-content {
      color: aliceblue;
      height: 220px;
      margin: auto;
      width: 90vw;
      display: flex;
      align-items: center;
      justify-content: space-between;

      .item {
        flex: 1;
        text-align: center;
      }
    }
  }
</style>
