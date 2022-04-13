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
		name: "NavBar"
	}
</script>

<style lang="less" scoped>
	#navBar{
		position: relative;
		display: flex;
		height: 60px;
		line-height: 60px;
		box-shadow: 0 1px 1px rgba(100,100,100,0.4);
		.left{
			flex: 1;
			font-size: 24px;
			line-height: 60px;
		}
		.middle{
			flex: 1;
		}
		.right{
			width: 120px;
			line-height: 60px;
		}
	}

</style>