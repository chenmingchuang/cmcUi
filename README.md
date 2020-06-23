allprojects {
		repositories {
			...
      //添加支持
			maven { url 'https://jitpack.io' }
		}
	}
  
  
  
  dependencies {
          导入依赖
	        implementation 'com.github.chenmingchuang:cmcui:Tag'
	}
