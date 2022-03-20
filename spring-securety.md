---
typora-root-url: img
typora-copy-images-to: img
---

# Spring Security

## 快速入门

### 	 1.导入相关依赖

```xml
<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
```

### 2.创建一个Controller

```java
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("hello")
    public String hello(){
        return "hello security";
    }
}

```

 启动启动项目访问项目 启动时会给你一个密码 可以直接登录

默认用户名是user 密码在启动时候会给你

![1647764547195](/1647764547195.png)

 这个时候访问页面就会来到这个登录界面 登录完成之后就会跳转到你访问的那个controller

![1647764619071](/1647764619071.png)

## 修改验证的登录名和密码

### 方法一

 通过配置文件直接修改登录的用户名和密码

```yml
spring:
  application:
    ##spring security通过配置文件修改登录的用户名和密码
  security:
    user:
      name: miku
      password: miku
```

### 方法二

通过添加配置文件去修改登录的用户名和密码

创建一个配置类

```java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //用于文件加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = bCryptPasswordEncoder.encode("miku");
        auth.inMemoryAuthentication().withUser("miku").password(password).roles("admin");
    }

    @Bean
    BCryptPasswordEncoder init(){
        return  new BCryptPasswordEncoder();
    }
}
```

### 方法三

通过自定义类实现

创建一个配置类

```java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //自己实现这个userDetailsService
    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
 auth.userDetailsService(userDetailsService).passwordEncoder(init());
    }

    @Bean
    BCryptPasswordEncoder init(){
        return  new BCryptPasswordEncoder();
    }
}
```

编写一个

```java
@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("role");
        return new User("miku",new BCryptPasswordEncoder().encode("miku"),auths);
    }
}

```

## 验证数据库

在传入时完成数据库查询

```java

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //数据库查询数据
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        Users userPo = userMapper.selectOne(wrapper);
        //判断是否为空
        if(userPo == null){
             throw new UsernameNotFoundException("用户不存在");
        }else{
            List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("role");
            return new User(userPo.getUsername(),new BCryptPasswordEncoder().encode(userPo.getPassword()),auths);
        }

    }
}
```

