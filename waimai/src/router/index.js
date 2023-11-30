import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store'

Vue.use(VueRouter)

const routes = [
  // {
  //   path: '/',
  //   component: () => import( '../views/LayOutView.vue'),
  //   redirect:"/home",
  //   children: [
  //     {
  //       path: 'home',
  //       name: '首页',
  //       component: () => import( '../views/MangerView/HomeView.vue'),
  //     },
  //     {
  //       path: 'user',
  //       name: '用户管理',
  //       component: () => import( '../views/MangerView/ShowUserView.vue'),
  //     },
  //     {
  //       path: 'role',
  //       name: '角色管理',
  //       component: () => import('../views/MangerView/RoleView.vue')
  //     },
  //     {
  //       path: 'file',
  //       name: '文件管理',
  //       component: () => import( '../views/MangerView/FileView.vue'),
  //     },
  //     {
  //       path: 'menu',
  //       name: '菜单管理',
  //       component: () => import( '../views/MangerView/MenuView.vue'),
  //     },
  //     {
  //       path: 'person',
  //       name: '个人信息',
  //       component: () => import('../views/MangerView/Person.vue')
  //     }
      
  //   ]
  // },
  
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/LoginView/LoginView.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/LoginView/RegisterView.vue')
  },
  {
    path: '/404',
    name: '404',
    component: () => import('../views/404.vue')
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

//刷新会重置
export const setRoutes = () =>{
  const storeMenu = localStorage.getItem("menu")
  if(storeMenu){
    const mangetRouter = {
      path: '/',
      component: () => import( '../views/LayOutView.vue'),
      redirect:"/home",
      children: [
        { path: 'person', name: '个人信息', component: () => import('../views/MangerView/Person.vue')},
      ]
    }

    const menu = JSON.parse(storeMenu)
    menu.forEach(item => {
      if (item.path) {//当且仅当path不为空的时候菜设置路由
        let itemMenu = {
          path: item.path.replace("/",""),
          name: 'Manger',
          component: () => import( '../views/MangerView/'+item.pagepath+'.vue'),
          
        }
        mangetRouter.children.push(itemMenu)
      }else if(item.children.length){
        item.children.forEach(item => {
          if(item.path){
            let itemMenu = {
              path: item.path.replace("/",""),
              name: item.name,
              component: () => import( '../views/MangerView/'+item.pagepath+'.vue'),
              
            }
            mangetRouter.children.push(itemMenu)
          }
         
        })
      }
     
      
    })
    const currentRouteName = router.getRoutes().map(v => v.id)
    if (!currentRouteName.includes('Manger')) {
      router.addRoute(mangetRouter)
    }
   
  }
}


setRoutes()



router.beforeEach((to, from, next) => {
  localStorage.setItem("currentPathName",to.name)//设置当前路由的名称
  store.commit("setPath")

  
  // 未找到路由的情况
  if (!to.matched.length) {
    const storeMenus = localStorage.getItem("menu")
    console.log(storeMenus);
    if (storeMenus) {
      next("/404")
    } else {
      // 跳回登录页面
      next("/login")
    }
  }

  next()
})

//成功解决NavigationDuplicated: Avoided redundant navigation to current location:
//当点击重复路径时
const originalPush = VueRouter.prototype.push
    //修改原型对象中的push方法
VueRouter.prototype.push = function push(location) {
        return originalPush.call(this, location).catch(err => err)
    }


export default router
