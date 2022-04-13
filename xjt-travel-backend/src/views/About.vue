<template>
  <div class="xjt-about-view">
    <div class="text-clip">管理员信息</div>

    <div class="about-description">
      <el-descriptions direction="vertical" :column="4" border>
        <el-descriptions-item label="用户名">{{loginUser.username}}</el-descriptions-item>
        <el-descriptions-item label="昵称">{{loginUser.nickname}}</el-descriptions-item>
        <el-descriptions-item label="生日">{{loginUser.birthday |filterFormatDate(that)}}</el-descriptions-item>

        <el-descriptions-item label="手机号">{{loginUser.telephone}}</el-descriptions-item>
        <el-descriptions-item label="邮箱">{{loginUser.email}}</el-descriptions-item>

        <el-descriptions-item label="用户头像" :span="2">
          <img v-lazy="loginUser.avatar" alt="" width="150px" height="150px">
        </el-descriptions-item>
      </el-descriptions>
    </div>

    <div class="text-clip">名人语录</div>
    <div class="quotes">
      <div class="quote-border-padding" v-for="(item,index) in quoteList" :key="index">
        {{item}}
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "About",
    data(){
      return{
        loginUser:this.$store.state.login_user,
        quoteList:[
          "人们宁愿去关心一个蹩脚电影演员的吃喝拉撒和鸡毛蒜皮，而不愿了解一个普通人波涛汹涌的内心…… ——路遥《平凡的世界》",
          "偏见使我无法爱别人，傲慢使别人无法来爱我。————《傲慢与偏见》",
          "世界上有两样东西不能直视，一是太阳，二是人心。——东野圭吾《白夜行》",
          "一个人可以被毁灭，但不能被打败。——海明威 《老人与海》",
          "任凭弱水三千，我只取一瓢饮。——《红楼梦》",
          "被真相伤害总比被谎言欺骗的好，得到了再失去，总是比从来就没有得到更伤人。——《追风筝的人》",
          "她可以褪色，可以枯萎，怎样都可以，但只要我看她一眼，万般柔情便涌上心头。——《洛丽塔》",
          "生活就像一座围城，城里的人想出去，城外的人想进来。————《围城》",
          "哭要一个人躲着哭，笑呢全世界陪你笑 ——亦舒《爱情之死》",
          "山有木兮木有枝，心悦君兮君不知 ——《越人歌》",
          "真正有气质的淑女，从不炫耀她所拥有的一切，她不告诉人她读过什么书，去过什么地方，有多少件衣服，买过什么珠宝，因为她没有自卑感。——《圆舞》",
        ],
      }
    }
  }
</script>

<style lang="less" scoped>
.xjt-about-view{
  margin: 20px auto;
  width: 90%;
  border: 1px solid #ddd;
  text-align: center;
  padding: 20px;
  .text-clip{
    padding: 10px 0;
    color:transparent;
    font-size: 40px;
    font-weight: bold;
    background: linear-gradient(45deg, rgba(0,173,181,1) 0%, rgba(0,173,181,.4)  100%);
    -webkit-background-clip: text;
  }
  .about-description{
    margin-top: 20px;
  }
  .quote-border-padding{
    border: 1px solid #eee;
    padding: 10px;
    margin-top: 10px;
    &:hover{
      background-color: #00ADB5;
      color: white;
    }
  }
}
</style>
