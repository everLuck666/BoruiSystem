(function(e){function n(n){for(var r,o,a=n[0],i=n[1],f=n[2],d=0,l=[];d<a.length;d++)o=a[d],Object.prototype.hasOwnProperty.call(c,o)&&c[o]&&l.push(c[o][0]),c[o]=0;for(r in i)Object.prototype.hasOwnProperty.call(i,r)&&(e[r]=i[r]);s&&s(n);while(l.length)l.shift()();return u.push.apply(u,f||[]),t()}function t(){for(var e,n=0;n<u.length;n++){for(var t=u[n],r=!0,o=1;o<t.length;o++){var a=t[o];0!==c[a]&&(r=!1)}r&&(u.splice(n--,1),e=i(i.s=t[0]))}return e}var r={},o={app:0},c={app:0},u=[];function a(e){return i.p+"js/"+({}[e]||e)+"."+{"chunk-115226b8":"aa373c91","chunk-0f200fc8":"3d753cb6","chunk-69118c62":"418f4946","chunk-1f7fd974":"ae27a307","chunk-55e280c8":"84499573","chunk-6ae77fde":"aa9358dc","chunk-9f76e6b0":"8deeddf0","chunk-ce250004":"1a00b6ab","chunk-cf274d04":"0d3144d1"}[e]+".js"}function i(n){if(r[n])return r[n].exports;var t=r[n]={i:n,l:!1,exports:{}};return e[n].call(t.exports,t,t.exports,i),t.l=!0,t.exports}i.e=function(e){var n=[],t={"chunk-0f200fc8":1,"chunk-69118c62":1,"chunk-1f7fd974":1,"chunk-55e280c8":1,"chunk-6ae77fde":1,"chunk-9f76e6b0":1,"chunk-ce250004":1,"chunk-cf274d04":1};o[e]?n.push(o[e]):0!==o[e]&&t[e]&&n.push(o[e]=new Promise((function(n,t){for(var r="css/"+({}[e]||e)+"."+{"chunk-115226b8":"31d6cfe0","chunk-0f200fc8":"fc6a1ff3","chunk-69118c62":"fe611bb0","chunk-1f7fd974":"8fe45e1c","chunk-55e280c8":"2cf3eb4d","chunk-6ae77fde":"71ed2695","chunk-9f76e6b0":"008d5800","chunk-ce250004":"679701b3","chunk-cf274d04":"c68de6bf"}[e]+".css",c=i.p+r,u=document.getElementsByTagName("link"),a=0;a<u.length;a++){var f=u[a],d=f.getAttribute("data-href")||f.getAttribute("href");if("stylesheet"===f.rel&&(d===r||d===c))return n()}var l=document.getElementsByTagName("style");for(a=0;a<l.length;a++){f=l[a],d=f.getAttribute("data-href");if(d===r||d===c)return n()}var s=document.createElement("link");s.rel="stylesheet",s.type="text/css",s.onload=n,s.onerror=function(n){var r=n&&n.target&&n.target.src||c,u=new Error("Loading CSS chunk "+e+" failed.\n("+r+")");u.code="CSS_CHUNK_LOAD_FAILED",u.request=r,delete o[e],s.parentNode.removeChild(s),t(u)},s.href=c;var h=document.getElementsByTagName("head")[0];h.appendChild(s)})).then((function(){o[e]=0})));var r=c[e];if(0!==r)if(r)n.push(r[2]);else{var u=new Promise((function(n,t){r=c[e]=[n,t]}));n.push(r[2]=u);var f,d=document.createElement("script");d.charset="utf-8",d.timeout=120,i.nc&&d.setAttribute("nonce",i.nc),d.src=a(e);var l=new Error;f=function(n){d.onerror=d.onload=null,clearTimeout(s);var t=c[e];if(0!==t){if(t){var r=n&&("load"===n.type?"missing":n.type),o=n&&n.target&&n.target.src;l.message="Loading chunk "+e+" failed.\n("+r+": "+o+")",l.name="ChunkLoadError",l.type=r,l.request=o,t[1](l)}c[e]=void 0}};var s=setTimeout((function(){f({type:"timeout",target:d})}),12e4);d.onerror=d.onload=f,document.head.appendChild(d)}return Promise.all(n)},i.m=e,i.c=r,i.d=function(e,n,t){i.o(e,n)||Object.defineProperty(e,n,{enumerable:!0,get:t})},i.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},i.t=function(e,n){if(1&n&&(e=i(e)),8&n)return e;if(4&n&&"object"===typeof e&&e&&e.__esModule)return e;var t=Object.create(null);if(i.r(t),Object.defineProperty(t,"default",{enumerable:!0,value:e}),2&n&&"string"!=typeof e)for(var r in e)i.d(t,r,function(n){return e[n]}.bind(null,r));return t},i.n=function(e){var n=e&&e.__esModule?function(){return e["default"]}:function(){return e};return i.d(n,"a",n),n},i.o=function(e,n){return Object.prototype.hasOwnProperty.call(e,n)},i.p="",i.oe=function(e){throw console.error(e),e};var f=window["webpackJsonp"]=window["webpackJsonp"]||[],d=f.push.bind(f);f.push=n,f=f.slice();for(var l=0;l<f.length;l++)n(f[l]);var s=d;u.push([0,"chunk-vendors"]),t()})({0:function(e,n,t){e.exports=t("56d7")},"034f":function(e,n,t){"use strict";t("85ec")},"56d7":function(e,n,t){"use strict";t.r(n);t("d3b7"),t("e260"),t("e6cf"),t("cca6"),t("a79d");var r=t("2b0e"),o=function(){var e=this,n=e.$createElement,t=e._self._c||n;return t("div",{attrs:{id:"app"}},[t("router-view")],1)},c=[],u=(t("034f"),t("2877")),a={},i=Object(u["a"])(a,o,c,!1,null,null,null),f=i.exports,d=t("a18c"),l=t("2f62");r["default"].use(l["a"]);var s=new l["a"].Store({state:{},mutations:{},actions:{},modules:{}}),h=t("5c96"),p=t.n(h),m=(t("0fae"),t("bc3a")),b=t.n(m),g=t("313e");r["default"].prototype.$echarts=g["default"],r["default"].config.productionTip=!1,r["default"].use(p.a),r["default"].prototype.$axios=b.a,b.a.defaults.baseURL="http://acfly.cn:8888",new r["default"]({router:d["a"],store:s,render:function(e){return e(f)}}).$mount("#app"),b.a.interceptors.request.use((function(e){return localStorage.token&&(e.headers.token=localStorage.token),e}),(function(e){return Promise.reject(e)})),b.a.interceptors.response.use((function(e){var n=e.data,t=n.state;return!1===t&&(location.href="#/login"),n}),(function(e){return Promise.reject(e)}))},"85ec":function(e,n,t){},a18c:function(e,n,t){"use strict";t("d3b7");var r=t("2b0e"),o=t("8c4f");r["default"].use(o["a"]);var c=[{path:"/",redirect:"/index/statistics"},{path:"/login",name:"Login",component:function(){return t.e("chunk-6ae77fde").then(t.bind(null,"dc3f"))},meta:{needLogin:!1}},{path:"/index",name:"Index",component:function(){return t.e("chunk-cf274d04").then(t.bind(null,"1e4b"))},meta:{needLogin:!0},children:[{path:"statistics",name:"Statistics",component:function(){return t.e("chunk-1f7fd974").then(t.bind(null,"d2b3"))},meta:{needLogin:!0}},{path:"order",name:"Order",component:function(){return Promise.all([t.e("chunk-115226b8"),t.e("chunk-69118c62")]).then(t.bind(null,"7915"))},meta:{needLogin:!0}},{path:"resource",name:"Resource",component:function(){return t.e("chunk-9f76e6b0").then(t.bind(null,"a116"))},meta:{needLogin:!0}},{path:"commodity",name:"Commodity",component:function(){return t.e("chunk-ce250004").then(t.bind(null,"74de"))},meta:{needLogin:!0}},{path:"customer",name:"Customer",component:function(){return Promise.all([t.e("chunk-115226b8"),t.e("chunk-0f200fc8")]).then(t.bind(null,"eabe"))},meta:{needLogin:!0}},{path:"system",name:"System",component:function(){return t.e("chunk-55e280c8").then(t.bind(null,"18d4"))},meta:{needLogin:!0}}]}],u=new o["a"]({routes:c,mode:"hash"});u.beforeEach((function(e,n,t){e.meta.needLogin?localStorage.getItem("token")?t():(t({path:"/login"}),alert("请登录后再访问主页！")):t()})),n["a"]=u}});
//# sourceMappingURL=app.f78b5dbb.js.map