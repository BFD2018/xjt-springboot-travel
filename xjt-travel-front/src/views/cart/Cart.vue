<template>
	<div class="cart-view">
		<nav-bar style="background-color:#66b1ff;color: white;">
			<div slot = "left-slot" style="border-left: 4px solid #fd8a4d;padding-left: 10px;font-size: 28px;font-weight: bold;">我的购物车</div>
			<div slot = "right-slot" style="font-size: 22px;">商品数量 {{tableData.length}}</div>
		</nav-bar>

		<el-table
				ref="multipleTable"
				tooltip-effect="dark"
				:data="tableData"
				@selection-change="handleSelectionChange"
				style="width: 100%">
			<el-table-column
					label="全选"
					type="selection"
					width="55">
			</el-table-column>

			<el-table-column
					width="120"
					label="封面图">
				<template slot-scope="scope">
					<el-image
							style="width: 100px; height: 100px;"
							:src="scope.row.route_photo.split(',')[0]"
							fit="fill"></el-image>
				</template>
			</el-table-column>

			<el-table-column
					label="商品">
				<template slot-scope="scope">
					<div class="title hvr-underline-from-center">{{ scope.row.title }}</div>
				</template>
			</el-table-column>

			<el-table-column
					label="单价"
					width="180">
				<template slot-scope="scope">
					￥{{ scope.row.price }}
				</template>
			</el-table-column>

			<el-table-column
					label="数量"
					width="250">
				<template slot-scope="scope">
					<el-input-number v-model="scope.row.goods_num" size="small"
													 @change="(value) => handleChange(value, scope.row.cart_id)" :min="1" :max="10"
													 label="数量"></el-input-number>
				</template>
			</el-table-column>

			<el-table-column
					label="小计"
					width="180">
				<template slot-scope="scope">
					￥{{ scope.row.price * scope.row.goods_num}}
				</template>
			</el-table-column>

			<el-table-column label="操作" width="150">
				<template slot-scope="scope">
					<el-popconfirm
							title="确定删除吗？">
						<el-button slot="reference" size="mini"
											 type="danger" @click="handleDelete(scope.row.cart_id)">删除</el-button>
					</el-popconfirm>
				</template>
			</el-table-column>
		</el-table>

		<div class="fixed-bottom">
			<nav-bar>
				<div slot="left-slot">
					<span>已选中 {{multipleSelection.length}} 件商品</span>
				</div>
				<div slot = "middle-slot" style="font-size: 22px;">
					<span>总价 ￥{{priceSum}}</span>
				</div>
				<div slot="right-slot">
					<el-button type="success" @click="dialogTableVisible = true">去结算</el-button>
				</div>
			</nav-bar>
		</div>

		<el-dialog title="请扫码支付" :visible.sync="dialogTableVisible" width="40%">
			<div class="dialog-wrapper" style="display: flex;margin: 0 auto;">
				<div class="left">
					<h3>微信支付</h3>
					<img :src="require('@/assets/images/common/wxpay.png')" alt="" width="300px" height="300px">
				</div>
				<div class="right" style="margin-left: 50px">
					<h3>支付宝支付</h3>
					<img :src="require('@/assets/images/common/alipay.png')" alt="" width="300px" height="300px">
				</div>
			</div>
		</el-dialog>
	</div>
</template>

<script>
	import NavBar from "@/components/navbar/NavBar.vue"

	export default {
		name: "Cart",
		components:{
			NavBar,
		},
		data(){
			return{
				inputSearchKey:"",

				tableData:[],
				multipleSelection: [],

				dialogTableVisible:false,
			}
		},
		methods:{
			initTableData(user_id){
				this.$getRequest("/cart/all/byUserid?user_id="+user_id).then(res =>{
					console.log(res);
					if(res.data.status === 200){
						this.tableData.splice(0)
						this.tableData.push(...res.data.obj)
					}
				})
			},
			handleSelectionChange(val) {
				console.log(val);
				this.multipleSelection = val;
			},

			handleDelete(cart_id){
				this.$getRequest("/delete/byCartid?cart_id="+cart_id).then(res =>{
					console.log(res);
					if(res.data.status === 200){
						this.$notify.success("成功移出购物车！")
					}
				})
			},
			handleChange(val,cart_id){
				this.$getRequest(`/cart/update/num/byCartid?cart_id=${cart_id}&num=${val}`).then(res =>{
					console.log(res);
					if(res.data.status === 200){
						this.$message.success(res.data.obj);
					}
				})
			},

		},
		created(){
			let user_id = this.$route.params.uid;
			console.log(user_id);

			this.initTableData(user_id);
		},
		computed:{
			/*计算总价*/
			priceSum() {
				let ret = this.multipleSelection.reduce((prev, current, index) =>{
					return prev + current.price * current.goods_num
				},0)
				return ret;
			}
		},
		mounted() {

		},
		watch:{

		}
	}
</script>

<style lang="less" scoped>
	.cart-view{
		position: relative;
		height: 100%;

		.fixed-bottom{
			margin-top: 250px;
			background-color: #eeeeee;
		}
	}
</style>
