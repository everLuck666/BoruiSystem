(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-1f7fd974"],{1276:function(t,e,n){"use strict";var r=n("d784"),a=n("44e7"),i=n("825a"),o=n("1d80"),s=n("4840"),c=n("8aa5"),u=n("50c4"),l=n("14c3"),f=n("9263"),d=n("d039"),p=[].push,h=Math.min,g=4294967295,v=!d((function(){return!RegExp(g,"y")}));r("split",2,(function(t,e,n){var r;return r="c"=="abbc".split(/(b)*/)[1]||4!="test".split(/(?:)/,-1).length||2!="ab".split(/(?:ab)*/).length||4!=".".split(/(.?)(.?)/).length||".".split(/()()/).length>1||"".split(/.?/).length?function(t,n){var r=String(o(this)),i=void 0===n?g:n>>>0;if(0===i)return[];if(void 0===t)return[r];if(!a(t))return e.call(r,t,i);var s,c,u,l=[],d=(t.ignoreCase?"i":"")+(t.multiline?"m":"")+(t.unicode?"u":"")+(t.sticky?"y":""),h=0,v=new RegExp(t.source,d+"g");while(s=f.call(v,r)){if(c=v.lastIndex,c>h&&(l.push(r.slice(h,s.index)),s.length>1&&s.index<r.length&&p.apply(l,s.slice(1)),u=s[0].length,h=c,l.length>=i))break;v.lastIndex===s.index&&v.lastIndex++}return h===r.length?!u&&v.test("")||l.push(""):l.push(r.slice(h)),l.length>i?l.slice(0,i):l}:"0".split(void 0,0).length?function(t,n){return void 0===t&&0===n?[]:e.call(this,t,n)}:e,[function(e,n){var a=o(this),i=void 0==e?void 0:e[t];return void 0!==i?i.call(e,a,n):r.call(String(a),e,n)},function(t,a){var o=n(r,t,this,a,r!==e);if(o.done)return o.value;var f=i(t),d=String(this),p=s(f,RegExp),m=f.unicode,x=(f.ignoreCase?"i":"")+(f.multiline?"m":"")+(f.unicode?"u":"")+(v?"y":"g"),y=new p(v?f:"^(?:"+f.source+")",x),b=void 0===a?g:a>>>0;if(0===b)return[];if(0===d.length)return null===l(y,d)?[d]:[];var E=0,S=0,A=[];while(S<d.length){y.lastIndex=v?S:0;var _,C=l(y,v?d:d.slice(S));if(null===C||(_=h(u(y.lastIndex+(v?0:S)),d.length))===E)S=c(d,S,m);else{if(A.push(d.slice(E,S)),A.length===b)return A;for(var I=1;I<=C.length-1;I++)if(A.push(C[I]),A.length===b)return A;S=E=_}}return A.push(d.slice(E)),A}]}),!v)},"13d5":function(t,e,n){"use strict";var r=n("23e7"),a=n("d58f").left,i=n("a640"),o=n("ae40"),s=n("2d00"),c=n("605d"),u=i("reduce"),l=o("reduce",{1:0}),f=!c&&s>79&&s<83;r({target:"Array",proto:!0,forced:!u||!l||f},{reduce:function(t){return a(this,t,arguments.length,arguments.length>1?arguments[1]:void 0)}})},"14c3":function(t,e,n){var r=n("c6b6"),a=n("9263");t.exports=function(t,e){var n=t.exec;if("function"===typeof n){var i=n.call(t,e);if("object"!==typeof i)throw TypeError("RegExp exec method returned something other than an Object or null");return i}if("RegExp"!==r(t))throw TypeError("RegExp#exec called on incompatible receiver");return a.call(t,e)}},"159b":function(t,e,n){var r=n("da84"),a=n("fdbc"),i=n("17c2"),o=n("9112");for(var s in a){var c=r[s],u=c&&c.prototype;if(u&&u.forEach!==i)try{o(u,"forEach",i)}catch(l){u.forEach=i}}},"17c2":function(t,e,n){"use strict";var r=n("b727").forEach,a=n("a640"),i=n("ae40"),o=a("forEach"),s=i("forEach");t.exports=o&&s?[].forEach:function(t){return r(this,t,arguments.length>1?arguments[1]:void 0)}},"1dde":function(t,e,n){var r=n("d039"),a=n("b622"),i=n("2d00"),o=a("species");t.exports=function(t){return i>=51||!r((function(){var e=[],n=e.constructor={};return n[o]=function(){return{foo:1}},1!==e[t](Boolean).foo}))}},"3e1b":function(t,e,n){"use strict";n("786f")},4160:function(t,e,n){"use strict";var r=n("23e7"),a=n("17c2");r({target:"Array",proto:!0,forced:[].forEach!=a},{forEach:a})},"44e7":function(t,e,n){var r=n("861d"),a=n("c6b6"),i=n("b622"),o=i("match");t.exports=function(t){var e;return r(t)&&(void 0!==(e=t[o])?!!e:"RegExp"==a(t))}},5899:function(t,e){t.exports="\t\n\v\f\r                　\u2028\u2029\ufeff"},"58a8":function(t,e,n){var r=n("1d80"),a=n("5899"),i="["+a+"]",o=RegExp("^"+i+i+"*"),s=RegExp(i+i+"*$"),c=function(t){return function(e){var n=String(r(e));return 1&t&&(n=n.replace(o,"")),2&t&&(n=n.replace(s,"")),n}};t.exports={start:c(1),end:c(2),trim:c(3)}},6547:function(t,e,n){var r=n("a691"),a=n("1d80"),i=function(t){return function(e,n){var i,o,s=String(a(e)),c=r(n),u=s.length;return c<0||c>=u?t?"":void 0:(i=s.charCodeAt(c),i<55296||i>56319||c+1===u||(o=s.charCodeAt(c+1))<56320||o>57343?t?s.charAt(c):i:t?s.slice(c,c+2):o-56320+(i-55296<<10)+65536)}};t.exports={codeAt:i(!1),charAt:i(!0)}},"65f0":function(t,e,n){var r=n("861d"),a=n("e8b5"),i=n("b622"),o=i("species");t.exports=function(t,e){var n;return a(t)&&(n=t.constructor,"function"!=typeof n||n!==Array&&!a(n.prototype)?r(n)&&(n=n[o],null===n&&(n=void 0)):n=void 0),new(void 0===n?Array:n)(0===e?0:e)}},7156:function(t,e,n){var r=n("861d"),a=n("d2bb");t.exports=function(t,e,n){var i,o;return a&&"function"==typeof(i=e.constructor)&&i!==n&&r(o=i.prototype)&&o!==n.prototype&&a(t,o),t}},"786f":function(t,e,n){},"8aa5":function(t,e,n){"use strict";var r=n("6547").charAt;t.exports=function(t,e,n){return e+(n?r(t,e).length:1)}},9263:function(t,e,n){"use strict";var r=n("ad6d"),a=n("9f7f"),i=RegExp.prototype.exec,o=String.prototype.replace,s=i,c=function(){var t=/a/,e=/b*/g;return i.call(t,"a"),i.call(e,"a"),0!==t.lastIndex||0!==e.lastIndex}(),u=a.UNSUPPORTED_Y||a.BROKEN_CARET,l=void 0!==/()??/.exec("")[1],f=c||l||u;f&&(s=function(t){var e,n,a,s,f=this,d=u&&f.sticky,p=r.call(f),h=f.source,g=0,v=t;return d&&(p=p.replace("y",""),-1===p.indexOf("g")&&(p+="g"),v=String(t).slice(f.lastIndex),f.lastIndex>0&&(!f.multiline||f.multiline&&"\n"!==t[f.lastIndex-1])&&(h="(?: "+h+")",v=" "+v,g++),n=new RegExp("^(?:"+h+")",p)),l&&(n=new RegExp("^"+h+"$(?!\\s)",p)),c&&(e=f.lastIndex),a=i.call(d?n:f,v),d?a?(a.input=a.input.slice(g),a[0]=a[0].slice(g),a.index=f.lastIndex,f.lastIndex+=a[0].length):f.lastIndex=0:c&&a&&(f.lastIndex=f.global?a.index+a[0].length:e),l&&a&&a.length>1&&o.call(a[0],n,(function(){for(s=1;s<arguments.length-2;s++)void 0===arguments[s]&&(a[s]=void 0)})),a}),t.exports=s},"9f7f":function(t,e,n){"use strict";var r=n("d039");function a(t,e){return RegExp(t,e)}e.UNSUPPORTED_Y=r((function(){var t=a("a","y");return t.lastIndex=2,null!=t.exec("abcd")})),e.BROKEN_CARET=r((function(){var t=a("^r","gy");return t.lastIndex=2,null!=t.exec("str")}))},a15b:function(t,e,n){"use strict";var r=n("23e7"),a=n("44ad"),i=n("fc6a"),o=n("a640"),s=[].join,c=a!=Object,u=o("join",",");r({target:"Array",proto:!0,forced:c||!u},{join:function(t){return s.call(i(this),void 0===t?",":t)}})},a623:function(t,e,n){"use strict";var r=n("23e7"),a=n("b727").every,i=n("a640"),o=n("ae40"),s=i("every"),c=o("every");r({target:"Array",proto:!0,forced:!s||!c},{every:function(t){return a(this,t,arguments.length>1?arguments[1]:void 0)}})},a640:function(t,e,n){"use strict";var r=n("d039");t.exports=function(t,e){var n=[][t];return!!n&&r((function(){n.call(null,e||function(){throw 1},1)}))}},a9e3:function(t,e,n){"use strict";var r=n("83ab"),a=n("da84"),i=n("94ca"),o=n("6eeb"),s=n("5135"),c=n("c6b6"),u=n("7156"),l=n("c04e"),f=n("d039"),d=n("7c73"),p=n("241c").f,h=n("06cf").f,g=n("9bf2").f,v=n("58a8").trim,m="Number",x=a[m],y=x.prototype,b=c(d(y))==m,E=function(t){var e,n,r,a,i,o,s,c,u=l(t,!1);if("string"==typeof u&&u.length>2)if(u=v(u),e=u.charCodeAt(0),43===e||45===e){if(n=u.charCodeAt(2),88===n||120===n)return NaN}else if(48===e){switch(u.charCodeAt(1)){case 66:case 98:r=2,a=49;break;case 79:case 111:r=8,a=55;break;default:return+u}for(i=u.slice(2),o=i.length,s=0;s<o;s++)if(c=i.charCodeAt(s),c<48||c>a)return NaN;return parseInt(i,r)}return+u};if(i(m,!x(" 0o1")||!x("0b1")||x("+0x1"))){for(var S,A=function(t){var e=arguments.length<1?0:t,n=this;return n instanceof A&&(b?f((function(){y.valueOf.call(n)})):c(n)!=m)?u(new x(E(e)),n,A):E(e)},_=r?p(x):"MAX_VALUE,MIN_VALUE,NaN,NEGATIVE_INFINITY,POSITIVE_INFINITY,EPSILON,isFinite,isInteger,isNaN,isSafeInteger,MAX_SAFE_INTEGER,MIN_SAFE_INTEGER,parseFloat,parseInt,isInteger,fromString,range".split(","),C=0;_.length>C;C++)s(x,S=_[C])&&!s(A,S)&&g(A,S,h(x,S));A.prototype=y,y.constructor=A,o(a,m,A)}},ac1f:function(t,e,n){"use strict";var r=n("23e7"),a=n("9263");r({target:"RegExp",proto:!0,forced:/./.exec!==a},{exec:a})},ad6d:function(t,e,n){"use strict";var r=n("825a");t.exports=function(){var t=r(this),e="";return t.global&&(e+="g"),t.ignoreCase&&(e+="i"),t.multiline&&(e+="m"),t.dotAll&&(e+="s"),t.unicode&&(e+="u"),t.sticky&&(e+="y"),e}},ae40:function(t,e,n){var r=n("83ab"),a=n("d039"),i=n("5135"),o=Object.defineProperty,s={},c=function(t){throw t};t.exports=function(t,e){if(i(s,t))return s[t];e||(e={});var n=[][t],u=!!i(e,"ACCESSORS")&&e.ACCESSORS,l=i(e,0)?e[0]:c,f=i(e,1)?e[1]:void 0;return s[t]=!!n&&!a((function(){if(u&&!r)return!0;var t={length:-1};u?o(t,1,{enumerable:!0,get:c}):t[1]=1,n.call(t,l,f)}))}},b727:function(t,e,n){var r=n("0366"),a=n("44ad"),i=n("7b0b"),o=n("50c4"),s=n("65f0"),c=[].push,u=function(t){var e=1==t,n=2==t,u=3==t,l=4==t,f=6==t,d=7==t,p=5==t||f;return function(h,g,v,m){for(var x,y,b=i(h),E=a(b),S=r(g,v,3),A=o(E.length),_=0,C=m||s,I=e?C(h,A):n||d?C(h,0):void 0;A>_;_++)if((p||_ in E)&&(x=E[_],y=S(x,_,b),t))if(e)I[_]=y;else if(y)switch(t){case 3:return!0;case 5:return x;case 6:return _;case 2:c.call(I,x)}else switch(t){case 4:return!1;case 7:c.call(I,x)}return f?-1:u||l?l:I}};t.exports={forEach:u(0),map:u(1),filter:u(2),some:u(3),every:u(4),find:u(5),findIndex:u(6),filterOut:u(7)}},d2b3:function(t,e,n){"use strict";n.r(e);var r=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{attrs:{id:"statistics"}},[n("div",{staticClass:"top"},[n("div",{staticClass:"btn"},[t._v("今日访客："+t._s(t.countData.todayVisitor)+"人")]),n("div",{staticClass:"btn"},[t._v("累计访客："+t._s(t.countData.sumVisitor)+"人")]),n("div",{staticClass:"btn"},[t._v("今日收入："+t._s(t.countData.todayGet)+"元")]),n("div",{staticClass:"btn"},[t._v("累计收入："+t._s(t.countData.sumGet)+"元")])]),n("div",{attrs:{id:"pic_visitor"}}),n("div",{attrs:{id:"pic_income"}}),n("div",{staticClass:"line"}),n("div",{staticClass:"order"},[n("div",{staticClass:"title"},[t._v("订单统计")]),n("div",{staticClass:"orderData"},[n("div",{staticClass:"btn left"},[t._v("待处理订单："+t._s(t.order.todo))]),n("div",{staticClass:"btn"},[t._v("已完成订单："+t._s(t.order.done))])])]),n("div",{staticClass:"line"}),n("div",{staticClass:"sales"},[n("div",{staticClass:"title"},[t._v("销量统计")]),n("el-table",{staticClass:"salesTable",attrs:{data:t.salesData,"show-summary":"","summary-method":t.getSummaries}},[n("el-table-column",{attrs:{prop:"productName",label:"商品名称","mid-width":"20%",align:"center"}}),n("el-table-column",{attrs:{prop:"productPrice",label:"商品单价","mid-width":"20%",align:"center"}}),n("el-table-column",{attrs:{prop:"today",label:"今日售出","mid-width":"20%",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(" "+t._s(e.row.today)+"件 ")]}}])}),n("el-table-column",{attrs:{prop:"month",label:"本月售出数量","mid-width":"20%",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(" "+t._s(e.row.month)+"件 ")]}}])}),n("el-table-column",{attrs:{prop:"total",label:"累计售出","mid-width":"20%",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(" "+t._s(e.row.total)+"件 ")]}}])})],1)],1)])},a=[],i=(n("a623"),n("4160"),n("a15b"),n("d81d"),n("13d5"),n("a9e3"),n("ac1f"),n("1276"),n("159b"),n("313e")),o={name:"Statistics",data:function(){return{countData:{todayVisitor:"",sumVisitor:"",todayGet:"",sumGet:""},salesData:[],order:{todo:"",done:""},x1:[],y1:[],x2:[],y2:[]}},methods:{getData:function(){this.getTodayPeople(),this.getAllPeople(),this.getTodayCount(),this.getAllCount(),this.getData1(),this.getData2(),this.getTodoOrder(),this.getDoneOrder(),this.getSalesTable(),this.getSalesTotal()},getTodayPeople:function(){var t=this;this.$axios.get("/count/today").then((function(e){200===e.status?t.countData.todayVisitor=e.data:t.$message.error(e.msg)})).catch((function(t){console.log(t)}))},getAllPeople:function(){var t=this;this.$axios.get("/count/all").then((function(e){console.log(e),200===e.status?t.countData.sumVisitor=e.data:t.$message.error(e.msg)})).catch((function(t){console.log(t)}))},getTodayCount:function(){var t=this;this.$axios.get("/orders/income").then((function(e){200===e.status?t.countData.todayGet=e.data:t.$message.error(e.msg)})).catch((function(t){console.log(t)}))},getAllCount:function(){var t=this;this.$axios.get("/orders/incomeMonth").then((function(e){200===e.status?t.countData.sumGet=e.data:t.$message.error(e.msg)})).catch((function(t){console.log(t)}))},getData1:function(){var t=this;this.$axios.get("/count/allCount").then((function(e){if(200===e.status){var n=e.data,r=n.map((function(t){return t.date})).join(",");t.x1=r.split(",");var a=n.map((function(t){return t.count})).join(",");t.y1=a.split(",").map(Number);var i=[{data:[],type:"line",label:{show:!0}}];i[0].data=t.y1,t.drawLine1(i,t.x1)}else t.$message.error(e.msg)})).catch((function(t){console.log(t)}))},getData2:function(){var t=this;this.$axios.get("/orders/all").then((function(e){if(200===e.status){var n=e.data,r=n.map((function(t){return t.date})).join(",");t.x2=r.split(",");var a=n.map((function(t){return t.income})).join(",");t.y2=a.split(",").map(Number);var i=[{data:[],type:"line",label:{show:!0}}];i[0].data=t.y2,t.drawLine2(i,t.x2)}else t.$message.error(e.msg)})).catch((function(t){console.log(t)}))},drawLine1:function(t,e){var n=i.init(document.getElementById("pic_visitor"));n.setOption({tooltip:{},xAxis:{name:"日期",data:e,type:"category",axisLine:{lineStyle:{color:"#BFBFBF"}}},yAxis:{name:"访客数量",type:"value",axisLine:{lineStyle:{color:"#BFBFBF"}}},series:t})},drawLine2:function(t,e){var n=i.init(document.getElementById("pic_income"));n.setOption({xAxis:{name:"日期",data:e,type:"category",axisLine:{lineStyle:{color:"#BFBFBF"}}},yAxis:{name:"收入",type:"value",axisLine:{lineStyle:{color:"#BFBFBF"}}},series:t})},getTodoOrder:function(){var t=this;this.$axios.get("/orders/waiting").then((function(e){200===e.status?t.order.todo=e.data:t.$message.error(e.msg)})).catch((function(t){console.log(t)}))},getDoneOrder:function(){var t=this;this.$axios.get("/orders/finished").then((function(e){200===e.status?t.order.done=e.data:t.$message.error(e.msg)})).catch((function(t){console.log(t)}))},getSalesTable:function(){var t=this;this.$axios.get("/orders/sales").then((function(e){200===e.status?t.salesData=e.data:t.$message.error(e.msg)})).catch((function(t){console.log(t)}))},getSalesTotal:function(){this.$axios.get("/orders/total").then((function(t){console.log(t)})).catch((function(t){console.log(t)}))},getSummaries:function(t){var e=t.columns,n=t.data,r=[];return e.forEach((function(t,e){if(0!==e)if(1!==e){var a=n.map((function(e){return Number(e[t.property])}));a.every((function(t){return isNaN(t)}))?r[e]="N/A":(r[e]=a.reduce((function(t,e){var n=Number(e);return isNaN(n)?t:t+e}),0),r[e]+="件")}else r[e]="合计";else r[e]=""})),r}},beforeMount:function(){this.getData()}},s=o,c=(n("3e1b"),n("2877")),u=Object(c["a"])(s,r,a,!1,null,"88ac275c",null);e["default"]=u.exports},d58f:function(t,e,n){var r=n("1c0b"),a=n("7b0b"),i=n("44ad"),o=n("50c4"),s=function(t){return function(e,n,s,c){r(n);var u=a(e),l=i(u),f=o(u.length),d=t?f-1:0,p=t?-1:1;if(s<2)while(1){if(d in l){c=l[d],d+=p;break}if(d+=p,t?d<0:f<=d)throw TypeError("Reduce of empty array with no initial value")}for(;t?d>=0:f>d;d+=p)d in l&&(c=n(c,l[d],d,u));return c}};t.exports={left:s(!1),right:s(!0)}},d784:function(t,e,n){"use strict";n("ac1f");var r=n("6eeb"),a=n("d039"),i=n("b622"),o=n("9263"),s=n("9112"),c=i("species"),u=!a((function(){var t=/./;return t.exec=function(){var t=[];return t.groups={a:"7"},t},"7"!=="".replace(t,"$<a>")})),l=function(){return"$0"==="a".replace(/./,"$0")}(),f=i("replace"),d=function(){return!!/./[f]&&""===/./[f]("a","$0")}(),p=!a((function(){var t=/(?:)/,e=t.exec;t.exec=function(){return e.apply(this,arguments)};var n="ab".split(t);return 2!==n.length||"a"!==n[0]||"b"!==n[1]}));t.exports=function(t,e,n,f){var h=i(t),g=!a((function(){var e={};return e[h]=function(){return 7},7!=""[t](e)})),v=g&&!a((function(){var e=!1,n=/a/;return"split"===t&&(n={},n.constructor={},n.constructor[c]=function(){return n},n.flags="",n[h]=/./[h]),n.exec=function(){return e=!0,null},n[h](""),!e}));if(!g||!v||"replace"===t&&(!u||!l||d)||"split"===t&&!p){var m=/./[h],x=n(h,""[t],(function(t,e,n,r,a){return e.exec===o?g&&!a?{done:!0,value:m.call(e,n,r)}:{done:!0,value:t.call(n,e,r)}:{done:!1}}),{REPLACE_KEEPS_$0:l,REGEXP_REPLACE_SUBSTITUTES_UNDEFINED_CAPTURE:d}),y=x[0],b=x[1];r(String.prototype,t,y),r(RegExp.prototype,h,2==e?function(t,e){return b.call(t,this,e)}:function(t){return b.call(t,this)})}f&&s(RegExp.prototype[h],"sham",!0)}},d81d:function(t,e,n){"use strict";var r=n("23e7"),a=n("b727").map,i=n("1dde"),o=n("ae40"),s=i("map"),c=o("map");r({target:"Array",proto:!0,forced:!s||!c},{map:function(t){return a(this,t,arguments.length>1?arguments[1]:void 0)}})},e8b5:function(t,e,n){var r=n("c6b6");t.exports=Array.isArray||function(t){return"Array"==r(t)}},fdbc:function(t,e){t.exports={CSSRuleList:0,CSSStyleDeclaration:0,CSSValueList:0,ClientRectList:0,DOMRectList:0,DOMStringList:0,DOMTokenList:1,DataTransferItemList:0,FileList:0,HTMLAllCollection:0,HTMLCollection:0,HTMLFormElement:0,HTMLSelectElement:0,MediaList:0,MimeTypeArray:0,NamedNodeMap:0,NodeList:1,PaintRequestList:0,Plugin:0,PluginArray:0,SVGLengthList:0,SVGNumberList:0,SVGPathSegList:0,SVGPointList:0,SVGStringList:0,SVGTransformList:0,SourceBufferList:0,StyleSheetList:0,TextTrackCueList:0,TextTrackList:0,TouchList:0}}}]);
//# sourceMappingURL=chunk-1f7fd974.ae27a307.js.map