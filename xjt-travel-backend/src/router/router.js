import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const Login = () => import('@/views/Login.vue')

const Home = () => import('@/views/Home.vue')
const Layout = () => import('@/layout/Index.vue')

const routes = [
	{ path: '/login',
		meta: { title: '登录', noCache: true },
		component: Login,
		hidden: true
	},
	{
		path: '/',
		component: Layout,
		children: [
			{
				path: '/dashboard',
				component: Home,
				name: 'Dashboard',
				meta: { title: '首页', icon: 'index', affix: true, noCache: true }
			},
      { path: '/about',
        meta: { title: '关于我', noCache: true },
        component: () => import('@/views/About.vue'),
      },
		]
	},
	{
		path: '/system',
		component: Layout,
		meta: { title: '系统管理' },
		children: [
			{
				path: '/system/user',
				component: () =>import('@/views/system/SysUser.vue'),
				name: '用户管理',
				meta: { title: '用户管理' },
			},
			{
				path: '/system/role_perm',
				component: () =>import('@/views/system/SysRole.vue'),
				name: '角色管理',
				meta: { title: '角色管理' },
			},
			{
				path: '/system/job',
				component: () =>import('@/views/system/SysJob.vue'),
				name: '岗位管理',
				meta: { title: '岗位管理' },
			},
		]
	},
	{
		path: '/travel',
		component: Layout,
		meta: { title: '游记' },
		children: [
			{
				path: '/travel/notelist',
				component: () =>import('@/views/travel/TravelNoteList.vue'),
				name: '游记列表',
				meta: { title: '游记列表' },
			},
			{
				path: '/travel/write',
				component: () =>import('@/views/travel/WriteTravel.vue'),
				name: '新增游记',
				meta: { title: '新增游记' },
			},
		]
	},
	{
		path: '/traveller',
		component: Layout,
		meta: { title: '旅行家' },
		children: [
			{
				path: '/traveller/list',
				component: () =>import('@/views/traveller/TravellerList.vue'),
				name: '旅行家列表',
				meta: { title: '旅行家列表' },
			},
			{
				path: '/traveller/add',
				component: () =>import('@/views/traveller/AddTraveller.vue'),
				name: '新增旅行家',
				meta: { title: '新增旅行家' },
			},
			{
				path: '/traveller/article/list',
				component: () =>import('@/views/traveller/ArticleList.vue'),
				name: '旅行家文章列表',
				meta: { title: '旅行家游记列表' },
			},
			{
				path: '/traveller/articles/:id',
				component: () =>import('@/views/traveller/TravellerArticles.vue'),
				name: '旅行家游记集',
				meta: { title: '旅行家游记集' },
			},
			{
				path: '/traveller/article/add',
				component: () =>import('@/views/traveller/AddArticel.vue'),
				name: '新增文章',
				meta: { title: '新增文章' },
			},
		]
	},
	{
		path: '/trips',
		component: Layout,
		meta: { title: '旅游路线' },
		children: [
			{
				path: '/trips/list',
				component: () =>import('@/views/route/RouteList.vue'),
				name: '路线列表',
				meta: { title: '路线列表' },
			},
			{
				path: '/trips/add',
				component: () =>import('@/views/route/RouteAdd.vue'),
				name: '新增旅游路线',
				meta: { title: '新增路线' },
			},
			{
				path: '/seller/list',
				component: () =>import('@/views/route/SellerList.vue'),
				name: '旅行社列表',
				meta: { title: '旅行社' },
			},
			{
				path: '/seller/routes',
				component: () =>import('@/views/route/SellerRoutes.vue'),
				name: '店铺商品',
				meta: { title: '店铺商品' },
			},
		]
	},
	{
		path: '/sys-tools',
		component: Layout,
		children: [
			{
				path: '/sys-tools/generator',
				component: () =>import('@/views/sys-tools/CodeGenerate.vue'),
				name: '代码生成',
        meta: { title: '代码生成' }
			},
			{
				path: '/sys-tools/storage',
				component: () =>import('@/views/sys-tools/StorageManage.vue'),
				name: '存储管理',
				meta: { title: '存储管理' }
			},
			{
				path: '/sys-tools/preview',
				component: () =>import('@/views/sys-tools/PreviewFile.vue'),
				name: '文件预览',
				meta: { title: '文件预览' }
			},
		]
	},
	{
		path: '/components',
		component: Layout,
		meta: { title: '组件管理' },
		children: [
			{
				path: '/components/echarts',
				component: () =>import('@/views/components/MyEcharts.vue'),
				name: '图表库',
				meta: { title: '图表库' }
			},
			{
				path: '/components/icon',
				component: () =>import('@/views/components/icons/index.vue'),
				name: '图标库',
				meta: { title: '图标库' }
			},
			{
				path: '/components/wangEditor',
				component: () =>import('@/views/components/MyWangEditor.vue'),
				name: '富文本',
				meta: { title: '富文本' }
			},
			{
				path: '/components/markdown',
				component: () =>import('@/views/components/MyMarkdown.vue'),
				name: 'Markdown',
				meta: { title: 'Markdown' }
			},
			{
				path: '/components/yaml',
				component: () =>import('@/views/components/MyYamlEdit.vue'),
				name: 'Yaml编辑器',
				meta: { title: 'Yaml编辑器' }
			},
		]
	},
]

const router = new VueRouter({
	routes,
	mode: "history"
})

export default router
