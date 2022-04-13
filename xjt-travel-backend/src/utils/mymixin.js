export default {
	install(Vue){
		Vue.mixin({
			data(){
				return{
					that:this,

					isShowBackTop:false,
				}
			},
			created(){

			},
			methods: {
				// getScrollTop() {
				// 	var scroll_top = 0;
				// 	if (document.documentElement && document.documentElement.scrollTop) {
				// 		scroll_top = document.documentElement.scrollTop;
				// 	} else if (document.body) {
				// 		scroll_top = document.body.scrollTop;
				// 	}
				// 	return scroll_top;
				// },

				backTopClick(){
					this.$refs.scroll.scrollTo();
				},


			},
			filters:{
				filterImgUrl(val,that){
					let imgurl = val + "";
					if(imgurl.startsWith("http") || imgurl.startsWith("https")){
						return val;
					}else{
						return that.$store.state.HOST + val;
					}
				},
				filterFormatDate(val,that){
					return that.$moment(val).format("YYYY-MM-DD");
				},
				filterFormatDateTime(val,that){
					return that.$moment(val).format("YYYY-MM-DD HH:mm:ss");
				},
			}
		})
	}
}