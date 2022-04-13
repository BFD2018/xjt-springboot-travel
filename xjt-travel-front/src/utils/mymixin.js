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
