<template>
	<div class="trip-detail-view" @scroll="barScrolling">
		<!--面包屑-->
		<el-breadcrumb separator="/">
			<el-breadcrumb-item>旅行商城首页</el-breadcrumb-item>
			<el-breadcrumb-item>{{routeObj.category_name}}</el-breadcrumb-item>
			<el-breadcrumb-item><span style="font-weight: bold;color: #fd8a4d;">{{routeObj.location}}</span></el-breadcrumb-item>
		</el-breadcrumb>

		<div class="trip-base">
			<!--左边：轮播图-->
			<div class="left" v-if="routeObj.route_photo">
				<el-carousel height="250px" direction="vertical" :interval="5000" indicator-position="none" arrow="hover">
					<el-carousel-item class="carousel-item" v-for="(item,index) in routeObj.route_photo.split(',')" :key="index">
						<el-image
								style="width: 100%; height: 100%;"
								:src="item"
								fit="fill"></el-image>
					</el-carousel-item>
				</el-carousel>
			</div>

			<!--右边：标题信息、价钱等信息-->
			<div class="right">
				<div class="title-wrapper">
					<el-tag type="default" effect="dark">店铺</el-tag>
					<span class="title">{{routeObj.title}}</span>
				</div>
				<div class="label">
					<span>价格天数玩法可调</span>
					<span>五星及保护区酒店</span>
					<span>网红玩法</span>
					<span>专业团队</span>
				</div>
				<div class="custom-panel">
					参考价：<span>￥{{routeObj.price}}</span>
					<i class="el-icon-star-off" style="font-size: 26px;margin-left: 30px"></i>{{routeObj.counts}} 收藏

					<el-button type="danger" style="margin-left: 50px" :disabled="hasAddToCart" @click="addToCart(routeObj.id)">加入购物车</el-button>
					<el-button type="warning" style="margin-left: 50px">立即预定</el-button>
				</div>
			</div>
		</div>

		<nav-bar>
			<div slot = "left-slot" style="border-left: 4px solid #fd8a4d;padding-left: 10px;">产品介绍</div>
		</nav-bar>

		<!--产品图文介绍-->
		<div class="detail-wrapper">
			<div class="detail-notes">
				<!--图文介绍-->
				<div class="img-item" v-for="(item,index) in routeImgs" :key="index">
					<img style="width: 100%;height: auto;min-height: 200px" v-lazy="item.pic"></img>
				</div>
			</div>

			<!--右侧信息-->
			<div class="extra-wrapper">
				<min-contact :seller-obj="{'seller_name':routeObj.seller_name,'seller_id':routeObj.seller_id}"></min-contact>

				<!--费用说明-->
				<div class="cost-desc" style="border: 1px solid #ddd;margin-top: 30px;">
					<nav-bar>
						<div slot = "left-slot" style="border-left: 5px solid #fd8a4d;padding-left: 15px">费用说明</div>
					</nav-bar>
					<div style="line-height: 45px;font-size: 22px;color: #fd8a4d;font-weight: bold;">费用包含：</div>
					<div style="padding: 0 10px;">
						<p>1. 机票：包含各地往返成都的机票；</p>
						<p>2. 包车：全程专属用车及专属司机服务（根据人数匹配车型；含油费、过路费、导游各项差旅补助等~每日正常工作时间不超过8小时）；</p>
						<p>3. 酒店：全程舒适型酒店住宿（酒店住宿为2人1间的标准间，可另定制不同档次酒店）；</p>
						<p>4. 保险：大脚国旅出资为其购买旅游意外险保额为30万元/人 (含急性病医疗)，若遇道路交通事故以及各种旅游意外将由保险公司进行赔付，大脚国旅有义务协助其办理保险事宜且不再承担其赔偿责任，不含财产险；</p>
						<p>5. 服务费：全程定制需求服务费；</p>
						<p>6. 服务包含：以上行程为经典行程，仅供参考，可根据自身需求个性化定制；</p>
						<p>温馨提示：由于定制的特殊性，页面价格仅供参考，需根据您的具体需求进行二次核价。</p>
					</div>
					<div style="line-height: 45px;font-size: 22px;color: #fd8a4d;font-weight: bold;">费用不含：</div>
					<div style="padding: 0 10px;">
						<p>1. 餐食：全程用餐（以定制行程为准，可根据客人喜好推荐特色餐食，丰俭由人，费用自理）；</p>
						<p>2. 门票：全程景点门票（可根据客人刚需程度，包含进去；也可自行决定游览项目，费用现付）；</p>
						<p>3. 其它：一切个人消费及费用包含中未提及的任何费用。</p>
					</div>
				</div>

				<!--游客须知-->
				<div class="should-know" style="margin-top: 30px;border: 1px solid #ddd;">
					<nav-bar>
						<div slot = "left-slot" style="border-left: 5px solid #fd8a4d;padding-left: 15px;">购买须知</div>
					</nav-bar>
					<div style="padding: 0 10px;">
						<p>1. 马蜂窝尊重并保护用户隐私，用户信息只会用于协助用户预订旅游商品；</p>
						<p>2. 出行前，请您确保您自身的身体状况适合参加行程，您应将自身健康情况如实告知旅行社； 并请尊重当地的饮食习惯、习俗禁忌、宗教礼仪， 遵守当地法律法规等；可仔细阅读 《旅游安全手册》， 普及自身旅游安全知识，遵守旅游文明公约，使旅程顺遂；</p>
						<p>3. 为了您人身、财产的安全，请您避免在公开场合暴露贵重物品及大量现金；</p>
						<p>4. 您所预订的商品可能包含高危项目（如游泳、漂流、潜水、滑雪等），参与前请根据自身条件， 并充分参考当地相关部门及其它专业机构的相关公告和建议后出行，出行前请仔细阅读 《安全防护指南》 ；</p>
						<p>5. 建议您自己购买旅游意外保险及其他保险，可保障您在境内旅行期间自身生命、 身体、财产或者相关利益的短期旅游意外伤害保险及紧急救援保险；</p>
						<p>6. 请您注意与旅行社签订旅游合同；</p>
						<p>7. 预订订单，需要在支付时限内支付全款，超出时限的支付将被视为无效支付， 系统将在1个工作日内将款项退回原支付的账号里，购买成功会收到确认邮件及短信。</p>
					</div>
				</div>
			</div>
		</div>

		<back-top @click.native="backTopClick" v-show="isShowBackTop">
			<img :src="require('@/assets/images/common/backtop.png')" alt = "">
		</back-top>

	</div>
</template>

<script>
	import NavBar from "@/components/navbar/NavBar.vue"
	import MinContact from "./comps/MinContact";
	import BackTop from "@/components/back-top/BackTop";

	export default {
		name: "TripDetail",
		components:{
			NavBar,
			MinContact,
			BackTop,
		},
		data(){
			return{
				routeObj:{},
				routeImgs:[],

				isShowBackTop:false,			//返回顶部
				hasAddToCart:false,
			}
		},
		methods:{
			initTripRoute(){
				this.$getRequest("/route/getById?id="+this.routeObj.id).then(res =>{
					console.log(res);
					if(res.data.status === 200){
						this.routeObj = res.data.obj;
					}
				})
			},
			initTripRouteImgs(){
				this.$getRequest("/route/getImgsById?id="+this.routeObj.id).then(res =>{
					console.log(res);
					if(res.data.status === 200){
						this.routeImgs.splice(0)
						this.routeImgs.push(...res.data.obj)
					}
				})
			},

			//加入购物车
			addToCart(route_id){
				/*https://cart.jd.com/tproduct?pid=100009464799&rid=0.04407159242660441   京东的加入购物车 post*/
				let data = {
					route_id:route_id,
					user_id:this.$store.state.login_user.userId,
				}
				this.$postRequest("/cart/add",data).then(res =>{
					console.log(res);
					if(res.data.status === 200){
						this.$message.success(res.data.obj)
						this.hasAddToCart = true;
					}
				})
			},

			//监听滚动
			barScrolling(){
				var scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop;
				if(parseFloat(scrollTop) > 1200){
					this.isShowBackTop = true;
				}else{
					this.isShowBackTop = false;
				}
			},
			//返回顶部
			backTopClick(){
				document.body.scrollTop = document.documentElement.scrollTop = 0
			},
		},
		mounted() {
			window.addEventListener('scroll', this.barScrolling)
		},
		created() {
			console.log(this.$route.params.id);
			this.routeObj.id = this.$route.params.id;

      this.initTripRoute();

			this.initTripRouteImgs();
		},
		beforeRouteLeave(to, form, next){
			window.removeEventListener('scroll',this.barScrolling);
			next();
		},
	}
</script>

<style lang="less" scoped>
	.trip-detail-view{
		width: 80%;
		/*background-color: #f4f7f8;*/
		margin-top: 10px;
		.trip-base{
			margin-top: 20px;
			display: flex;
			/*border-bottom: 1px solid #dddddd;*/
			padding-bottom: 20px;
			.left{
				width: 420px;
				height: 250px;
				overflow: hidden;
			}
			.right{
				flex: 1;
				margin-left: 30px;
				.title{
					margin-left: 10px;
					font-size: 24px;
					line-height: 50px;
					font-weight: bold;
				}
				.label{
					line-height: 60px;
					span{
						padding: 5px;
						border: 1px dashed #fd8a4d;
						margin-left: 15px;
					}
				}
				.custom-panel{
					line-height: 60px;
					padding: 10px;
					background-color: #eeeeee;
					span{
						font-size: 28px;
						font-weight: bold;
						color: #fd8a4d;
					}
				}
			}
		}

		.detail-wrapper{
			margin-top: 20px;
			display: flex;
			.detail-notes{
				flex: 1;
				.img-item{
					width: 100%;
					height: auto;
				}
			}
			.extra-wrapper{
				width: 450px;
				margin-left: 30px;
			}
		}
	}
</style>
