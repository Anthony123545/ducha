import Vue from "vue";
import Vuex from "vuex";
import app from "./modules/app";
import user from "./modules/user";
import tagsView from "./modules/tagsView";
import permission from "./modules/permission";
import settings from "./modules/settings";
import getters from "./getters";
import WebSocketService from '@/utils/WebSocketService';
import ws from './modules/ws';

Vue.use(Vuex);

const store = new Vuex.Store({
  state:{
    fileName:""
  },
  modules: {
    app,
    user,
    tagsView,
    permission,
    settings,
    ws,
  },
  getters
});

// 注入 store 给 WebSocketService
WebSocketService.setStore(store);

export default store;
