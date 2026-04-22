// WebSocketService.js

class WebSocketService {
  constructor() {
    this.websocket = null;
    this.heartbeatTimer = null;
    this.reconnectTimer = null;
    this.token = null;
    this.store = null; // 注入 Vuex store
  }

  setStore(store) {
    this.store = store;
  }

  initWebSocket(token) {
    if (!token) {
      console.warn("❌ WebSocket token 为空");
      this.logoutAndRedirect();
      return;
    }

    // 如果已连接或连接中，跳过
    if (this.websocket &&
      (this.websocket.readyState === WebSocket.OPEN || this.websocket.readyState === WebSocket.CONNECTING)) {
      console.log("ℹ️ WebSocket 已连接或连接中，跳过重复连接");
      return;
    }

    this.token = token;
    const wsUrl = `${process.env.VUE_APP_WS_URL}/${token}`;
    console.log("🔌 WebSocket连接中：", wsUrl);

    this.websocket = new WebSocket(wsUrl);
    this.websocket.onopen = this.websocketonopen.bind(this);
    this.websocket.onerror = this.websocketonerror.bind(this);
    this.websocket.onmessage = this.setOnmessageMessage.bind(this);
    this.websocket.onclose = this.websocketclose.bind(this);

    // 初始化状态为 connecting
    this.updateState("connecting"); // ✅ 直接更新 Vuex
  }

  websocketonopen() {
    console.log("✅ WebSocket连接成功");
    this.startHeartbeat();
    this.updateState("open"); // ✅ 直接更新 Vuex
  }

  websocketonerror(e) {
    console.error("❌ WebSocket连接错误:", e);
    this.updateState("error"); // ✅ 直接更新 Vuex
    this.reconnect();
  }

  websocketclose(e) {
    console.warn("⚠️ WebSocket连接关闭:", e);
    this.stopHeartbeat();
    this.updateState("closed"); // ✅ 直接更新 Vuex
    this.reconnect();
  }

  reconnect() {
    if (this.reconnectTimer) return;

    // ✅ 使用注入的 store
    const token = this.store?.getters?.token;
    if (!token) {
      console.warn("⚠️ 无法重连：token 为空，可能用户已登出");
      return;
    }

    if (
      this.websocket &&
      (this.websocket.readyState === WebSocket.OPEN ||
        this.websocket.readyState === WebSocket.CONNECTING)
    ) {
      console.log("ℹ️ WebSocket 已连接，无需重连");
      return;
    }

    this.reconnectTimer = setTimeout(() => {
      console.warn("🔁 尝试重新连接 WebSocket...", token);
      this.initWebSocket(token);
      this.reconnectTimer = null;
    }, 5000);
  }

  setOnmessageMessage(event) {
    try {
      const msg = JSON.parse(event.data);
      console.log("📩 收到消息:", msg);
      if (msg.type) {
        window.dispatchEvent(new CustomEvent(msg.type, { detail: msg }));
      }
    } catch (err) {
      console.error("WebSocket消息解析失败:", err);
    }
  }

  startHeartbeat() {
    this.stopHeartbeat();
    this.heartbeatTimer = setInterval(() => {
      if (this.websocket && this.websocket.readyState === WebSocket.OPEN) {
        this.websocket.send(JSON.stringify({ type: "heartbeat", time: new Date().toISOString() }));
      }
    }, 30000);
  }

  stopHeartbeat() {
    if (this.heartbeatTimer) {
      clearInterval(this.heartbeatTimer);
      this.heartbeatTimer = null;
    }
  }

  updateState(status) {
    if (this.store) {
      this.store.commit("ws/setWebSocketStatus", status);
    }
  }

  destroy() {
    this.stopHeartbeat();
    if (this.websocket) {
      this.websocket.close();
      this.websocket = null;
    }
    console.log("🧹 WebSocket 已销毁");
    this.updateState("closed");
  }

  close() {
    this.destroy();
  }

  logoutAndRedirect() {
    if (this.store) {
      this.store.dispatch("LogOut").finally(() => {
        location.href = "/index";
      });
    } else {
      location.href = "/index";
    }
  }
}

export default new WebSocketService();
