# Spring-Boot-Storm
## Why create this? (工程目标)
- [EN] In order to facilitate people to quickly access the Storm data stream processing system.
- [ZH]为了方便大家快速接入使用Storm数据流处理系统。
## How can I do this? (实施办法)
- [EN] You first need to add these two dependencies to the project dependency script,
and then resolve a series of dependency conflicts
- [ZH] 首先需要在工程依赖脚本中，加入这两个依赖项，然后就是解决一系列依赖冲突。
'''
implementation('org.springframework.boot:spring-boot-starter-web')
compile('org.apache.storm:storm-core:1.2.2')
'''

## Dost it work well? (实际效果)
- [EN] Yes,it works good.In this project,I'm using Spring's exposed Controller component to control Storm's work.
- [ZH]还不错，在这个项目里，我用的是Spring暴露的Controller组件来控制Storm工作的.