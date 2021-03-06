# MVP模式扩展-->MVPH模式
#### 使用简单，易扩展，易维护，低耦合，高复用是MVPH的目标<br>
![](https://img.shields.io/badge/JitPack-0.1.2-green.svg)![](https://img.shields.io/badge/DemoVersion-1.5-yellow.svg)![](https://img.shields.io/badge/作者-xujl-ff69b4.svg)<br>

引用方式 :<br>

> **compile 'com.xujl:BaseLibrary:0.1.2'**<br>

### [架构思路简介](https://github.com/AcgnCodeMonkey/MVPLibrary/blob/master/file/架构思路.md)
&emsp;&emsp;MVP的基本思想这里不做过多解释，有兴趣的同学可以在网上找相应资料学习一下。<br>
&emsp;&emsp;比较深入学习过MVP模式的同学都知道，MVP现在比较大众的使用方法有两种:<br>
1.  **使用activity作为view层，创建一个presenter和model与之对应，配合使用。**
2.  **使用activity作为presenter层，创建一个view和model与之对应，配合使用。**<br>

&emsp;&emsp;这两种方式各有优劣，这里不做过多阐述，本库采用的是第二种方式实现。MVPH与传统MVP的最大区别在于逻辑的实现上。以model为例，
传统MVP中通常会把存储数据的逻辑写在某些BaseModel中，这样Presenter需要获取某些数据时就可以直接调用
Model中封装好的对应方法。一般来讲这么写没有太大问题，但是特别的，如果我们遇到某个特定业务，需要处理某些
数据，并且这个处理有可能还会出现在其他很多界面，此时基于传统MVP通常会有两种方法来解决:
1.  **因为一个Presenter是可以包含多个Model的，所以让多个需要处理这种业务的Presenter都引用这个处理数据的Model**
2.  **封装这段处理业务的逻辑成为一个新的BaseModel，这样，其他地方的Model只需要继承这个Model就能包含这段业务处理能力**

&emsp;&emsp;以上两种方法都有比较致命的问题，第一种方法的问题在于，Presenter虽然可以对应多个Model，但是通常每个Model会有自己
比较特别的业务逻辑，如果直接引用，会造成一些不该出现的方法也能被Presenter调用，而且采用一个p对应多个m，会造成对Model管理的复杂度增加。第二种方法的问题就更明显了，继承已经是最大的问题
，由于Java中类只能单继承，所以说当你继承了这个特定业务的Model时就代表无法再去继承其他类了，那么问题来了，如果这时需求变更，
又突然出现一个需要多次使用的数据处理逻辑，并且和之前的Model没有任何联系时，你要怎么办呢？<br>

&emsp;&emsp;当然，这种问题对于有一定经验的程序猿当然是没有任何难度的，我们通常可以选择单独封装一个类来处理一部分特定通用逻辑
，这样Model再去引用这个Helper类就能使用通用的数据处理逻辑了。这个其实就是大家常说的少用继承多用组合的设计原则。<br>

> **MVPH的核心思想也正是基于这种思想，MVP只提供基本的设计框架，实际的业务逻辑（这里特指那些很多界面多次出现的业务逻辑）
全部交由每种业务对应的ViewHelper,PresenterHelper，ModelHelper去处理具体逻辑，MVP各个模块只管调用Helper
类中几个方法就能实现需要的业务，采用这个方法把需要复用的逻辑独立与MVP之外的Helper类。**

##### 总结
&emsp;&emsp;总的来说MVPH与传统MVP的区别在于，传统MVP对于复用逻辑的是一个View对应多个Presenter或Model(如果是以
Activity为Presenter则是一个Presenter对应多个View和Model),而MVPH的思想则是MVP只能一一对应，即一个Presenter
（Activity）对应一个View和一个Model，对于需要复用的逻辑采用组合的方式使用Helper类来实现，以达到逻辑和数据甚至视图的
多次复用，同时每个helper由mvp每个对应角色自己管理，这样，一个model可以只由自己独立的逻辑构成也可以自主或由Presenter添加某个通用功能的helper到自己内部进行功能扩展。<br>
&emsp;&emsp;注意点：虽然思想是这样的，但是我们在编写helper类的时候应该有自己的把控，因为通常helper的编写就涉及到三个，ViewHelper,ModelHelper,PresenterHelper,就像mvp一样，如果对于helper划分的粒度过细的话，非常容易造成类爆炸（mvp划分不好会造成方法数爆炸）。所以，目前来讲，只建议抽离通用性较强或者虽然使用的地方不多，但是逻辑非常复杂的业务逻辑，这样才能达到一个平衡点。

***
### 框架功能介绍
支持的功能:
>1.  **ToolBar动态加载.**无需每个布局引用相同的ToolBar布局，只需配置一次ToolBarModule实现类。同时，支持每个activity
> 单独修改ToolBar。<br>
>
>2.  **动态权限管理集成.**框架使用了第三方权限管理库easypermissions，需要申请权限的activity只需要复写下面的方法
> 并返回需要申请的对应权限数组即可，当然你也可以自己引用其他库处理权限，这并不会冲突。<br>
>  String[] needPermissions ();

>3. **框架中集成了activity栈管理.**可以方便的一键退出所有activity或某几个activity，具体使用参考ActivityManager类

>4. **支持关闭MVP模式进行开发.**我们都知道，app中某些界面的逻辑有时候非常简单，并且基本上不用过多考虑扩展性的问题，这时，使用MVP模式进行开发无疑是臃肿的，因为你可能不得不为了几行显示数据的逻辑去给他写上几个接口和类。所以MVPH提供了方法可以关闭MVP模式，让你重回MVC模式，通常你只需要复写下面的方法并返回false就可以了。而且只需要在你自己的实现基类稍作处理，就算不使用MVP模式，你依然可以使用View和Model调用他们中基础方法。<br>
>  boolean isMVP();//是否使用MVP模式

>5. **支持DataBinding.**从0.1.0版本开始，正式兼容使用DataBinding进行开发


&emsp;&emsp;由于为了提高框架的自由度与可扩展度，所以MVPH本身并没有封装太多功能，仅仅提供了基本的MVPH架构思路。不过在demo里
我展示了通过使用MVPH框架为基础进行扩展的一个简单套路，目前demo还比较简单，打算在后面丰富demo的功能，主要是涵盖
开发者们的大部分业务场景，这样大家在遇到一些特别的界面时能有一个参考进行开发。

***
[1.属性方法说明](https://github.com/AcgnCodeMonkey/MVPLibrary/blob/master/file/Method%20description.md)<br>
[2.部分功能说明](https://github.com/AcgnCodeMonkey/MVPLibrary/blob/master/file/special.md)

###### 交流群:275885217&emsp;&emsp;入群密码:mvp
###### 友情鸣谢：[接口提供-http://gank.io/api](http://gank.io/api)
---
## 版本更新日志:

**更新日期：2017/09/25  库版本：0.1.2  demo版本：1.5**
* presenter新增通过类名反射创建view和model，可以不用再传递类名
* 抽取部分方法到接口
* 修改activity加载流程，采用界面完全可见时才进行逻辑初始化，防止初始化时进行popupWindow弹窗引起的异常
* 增加说明文档

**更新日期：2017/09/20  库版本：0.1.1  demo版本：1.4**
* 新增mvp基础框架支持dataBinding
* 布局加载逻辑统一由BaseViewHelper进行控制，加载配置由新增类LayoutConfig进行
     控制
* 优化view和presenter的部分加载逻辑，去除部分无用方法，简化调用逻辑
* 修正部分不规范的方法名，逻辑复杂处添加更多注释
* 修改BaseFragment懒加载的部分代码
* demo中原dataBinding示例界面，改为直接继承CommonPresenter,去除之前封装的
     dataBindingPresenter等类
* 新增方法说明文档（持续更新，逐步完善）


**更新日期：2017/09/18  库版本：0.0.9  demo版本：1.3**
* 更改helper基类用法，基础model，view，presenter，helper类改为继承BaseMvpHelper
     （原BaseHelper）类，新的BaseHelper类为其他自定义helper类的基类，并且只有
     基础BaseMvpHelper的子类才具有添加BaseHelper类的功能（之前是任意BaseHelper
     子类都可以添加）,自定义的helper类无法往自己内部添加helper类。
* baseView新增findView方法，可以直接调用，不用再需要使用mRootView,也不需要类型强转
* demo依赖RxLibrary方式变更
* demo资讯新增viewPage+fragment分类，增加启动页面，首页导航增加二维码扫描
* demo的AppLibrary新增结合DataBinding的使用封装基类，demo中新增结合DataBinding的使用
     示例，下次更新会更改为基础库支持DataBinding。
* 修改基础库部分字段访问权限
* 下次更新目标：优化ToolBarModule和helper类代码，优化view和presenter的模板代码，释放
     部分逻辑到helper类中，基类兼容DataBinding

**更新日期：2017/09/6  库版本：0.0.8  demo版本：1.2**
* 新增baseview可控制在不使用toobar时是否为布局添加父布局
* 修复activity和fragment销毁时未清空model和view引用
* demo更新，引入rxjava2,新增RxLibrary,修改demo部分加载逻辑
* demo首页变更，新增安卓资讯栏目，点击跳转webview详情页,详情页采用非mvp编写

**更新日期：2017/07/24  库版本：0.0.6  demo版本：1.1**
* 修改基础库BaseView加载判断，兼容activity和fragment
* 优化BaseToolBarModule加载逻辑，支持页面本身包含toolbar布局
* 修复权限弹窗可以被关闭的Bug

**更新日期：2017/07/14  库版本：0.0.4  demo版本：1.1**
* 修改基础库方法加载顺序，防止动态授权时引起的空指针
* 优化toolbar，改为view引用toolbar而不是presenter引用toolbar
* demo新增图片搜索，收藏，下载功能

**更新日期：2017/07/06  库版本：0.0.3  demo版本：1.0**
* 修改exposeActivity方法返回值类型
* 优化部分类方法
* 从此版本开始，框架库接入了我自己的正式项目中

**更新日期：2017/07/05  库版本：0.0.1  demo版本：1.0**
* 上传初步基础框架
* 完成简单demo基础Library封装
* 编写简单demo

## Licence

```
Copyright 2016 Shintaro Katafuchi, Marcel Schnelle, Yoshinori Isogai

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
