<template>
	<div id="navBar">
		<div class="left">
			<slot name="left-slot"></slot>
		</div>
		<div class="middle">
			<slot name="middle-slot"></slot>
		</div>
		<div class="right">
			<slot name="right-slot"></slot>
		</div>
	</div>
</template>

<script>
	export default {
		name: "MyNav"
	}
</script>

<style lang="less" scoped>
	#navBar{
		position: relative;
		display: flex;
		height: 60px;
		line-height: 60px;
		border-bottom: 1px solid #eee;
		margin-bottom: 10px;
		.left{
			flex: 1;
			font-size: 22px;
			line-height: 60px;
			text-align: left;
		}
		.middle{
			flex: 1;
		}
		.right{
			width: 120px;
			line-height: 60px;
			text-align: right;
		}
	}

</style>
