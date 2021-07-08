# node-cli-generator
node-cli 项目快速脚手架

## Feature
通过配置快速生成一个**node-cli**工程，只需要继续开发相关功能即可，省去重写配置的步骤

## Usage
1. 修改application.yml配置文件中的cli部分内容
2. 启动项目
3. 将目录下的output目录拷贝，该目录即为生成好的node-cli项目
4. 使用npm install初始化，进行功能开发即可

## Example

一个示例cli配置如下：

```yaml
# cli工具配置
generator:
  cli:
    # cli工具名称
    name: "my-cli"

    # cli工具版本
    version: "0.0.1"

    # cli工具描述
    description: "我的cli命令行工具"

    # cli工具git代码仓库
    repo: "https://git.woa.com/larkzhang/cli-generator.git"

    # cli工具作者
    author: "larkzhang <larkzhang@tencent.com>"

    # cli工具开源协议
    license: "MIT"

    # cli工具可执行名称（包含别名）
    bin:
      - my-tool
      - mtool
      - mtl

    # 子命令
    commands:
        # 子命令名称
      - name: "list"
        # 子命令描述
        description: "列表查询"
        # 子命令主函数
        action: "listAction"
        # 子命令参数
        options:
            # 参数定义 （参考commander的option写法：https://github.com/tj/commander.js/blob/master/Readme_zh-CN.md#%e9%80%89%e9%a1%b9）
          - flags: "-cp, --client-port <port>"
            # 参数描述
            description: "指定PC端口"
            # 参数回调（预处理参数，可选）
            callback: "callbackFunc1"
            # 参数默认值（可选）
            value: "8765"

          - flags: "-sp, --server-port <port>"
            description: "指定测试机端口"

      - name: "record"
        description: "开始录制"
        action: "recordAction"
        options:
          - flags: "-o, --output <file>"
            description: "指定录制输出"

    # 全局参数
    options:
      - flags: "-a --all"
        description: "指定全部"
        callback: "callbackFunc2"
```

自动生成效果如下：

```shell script

➜ my-tool        
Usage: my-tool [options] [command]

Options:
  -V, --version     output the version number
  -a --all          指定全部
  -h, --help        display help for command

Commands:
  list [options]    列表查询
  record [options]  开始录制
  help [command]    display help for command

# ----------------------------------------------------------

# 使用别名
➜ mtl list -h
Usage: mtl list [options]

列表查询

Options:
  -cp, --client-port <port>  指定PC端口 (default: "8765")
  -sp, --server-port <port>  指定测试机端口
  -h, --help                 display help for command
➜  my-tool 
```