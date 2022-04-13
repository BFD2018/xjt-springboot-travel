import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const Login = () => import('@/views/Login.vue')
const Register = () => import('@/views/Register.vue')

const Index = () => import('@/views/Index.vue')
const Home = () => import('@/views/home/Home.vue')

//旅行家
const Traveller = () => import('@/views/traveller/Traveller.vue')
const TravellerAuthor = () => import('@/views/traveller/TravellerAuthor.vue')
const TravellerArticleDetail = () => import('@/views/traveller/TravellerArticleDetail.vue')

//游记详情
const NoteDetail = () => import('@/views/travelnote/NoteDetail.vue')
const WriteTravelNote = () => import('@/views/travelnote/WriteTravelNote.vue')


const Guide = () => import('@/views/guides/Guide.vue')

const ToTrip = () => import('@/views/trips/ToTrip.vue')
const TripDetail = () => import('@/views/trips/TripDetail.vue')
const SellerProduct = () => import('@/views/trips/SellerProduct.vue')

const Cart = () => import('@/views/cart/Cart.vue')

const BBS = () => import('@/views/bbs/BBS.vue')


//关于我
const About = () => import('@/views/about/About.vue')

const routes = [
	{
		path: '/login',
		name: 'login',
		component: Login
	},
	{
		path: '/register',
		name: 'register',
		component: Register
	},
	{
		path: '/',
		name: '首页',
		component: Index,
		redirect: "/home",
		children: [
			{path: "/home", name: "首页", component: Home},
			//普通游记
			{path: "/travelnote/:id", name: "游记详情", component: NoteDetail},
			{path: "/write/travelnote", name: "写游记", component: WriteTravelNote},

			{path: "/traveller/article/:id", name: "旅行家的游记", component: TravellerArticleDetail},
			{path: "/traveller", name: "旅行家专栏", component: Traveller},
			{path: "/traveller/author/:id", name: "旅行家的全部作品", component: TravellerAuthor},

			{path: "/guides", name: "旅游攻略", component: Guide},

			{path: "/trips", name: "去旅行", component: ToTrip},
			{path: "/trips/:id", name: "旅行路线详情", component: TripDetail},
			{path: "/seller/:id", name: "旅行社", component: SellerProduct},
			{path: "/cart/:uid", name: "购物车", component: Cart},

			{path: "/bbs", name: "社区", component: BBS,},
			{path: "/about", name: "关于我", component: About},
		]
	},

]

const routers = new VueRouter({
	routes,
	mode: "history"
})

export default routers
