<template>
	<div class="back-top">
		<slot></slot>
	</div>
</template>

<script>
	export default {
		name: "BackTop",
		methods:{
			//也可以使用@click.native修饰符，让组件也能发出监听点击事件
			// backTop(){
			// 	this.$emit("backTop")
			// }
		}
	}
</script>

<style scoped>
	.back-top{
		position: fixed;
		bottom: 50px;
		right: 50px;
		z-index: 999;
		background-color: #f2f5f6;
	}
	.back-top img{
		width: 60px;
		height: 60px;
	}
</style>