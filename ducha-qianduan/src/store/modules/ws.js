import WebSocketService from "@/utils/WebSocketService";

export default {
  namespaced: true,
  state: {
    webSocketState: "closed", // 'open' | 'connecting' | 'error' | 'closed'
  },
  mutations: {
    setWebSocketStatus(state, status) {
      console.log("WebSocket状态已更新:", status);
      state.webSocketState = status;
    },
  },
  actions: {
    connectWebSocket({ commit, rootState }) {
      const token = rootState.user?.token;
      WebSocketService.setStore(this); // 注入 store
      commit("setWebSocketStatus", "connecting");
      WebSocketService.initWebSocket(token);
    },

    reconnectWebSocket({ state, commit }) {
      if (state.webSocketState === "open" || state.webSocketState === "connecting") {
        console.log("WebSocket 已连接或正在连接中，跳过重连");
        return;
      }
      commit("setWebSocketStatus", "connecting");
      WebSocketService.reconnect();
    },

    closeWebSocket() {
      WebSocketService.close();
    }
  }
};
