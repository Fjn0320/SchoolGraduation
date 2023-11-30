import Vue from 'vue'
import App from './App.vue'
import router from './router'
//引入ElementUI组件
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

import './assets/gloable.css'
import request from '@/until/request'
import store from './store';

//然后使用
Vue.use(ElementUI,{size:"mini"});

Vue.prototype.request = request

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
