package com.edu.boot;

import com.edu.boot.common.MD5Utils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.edu.boot.mapper")
@SpringBootApplication
public class BootAdjunctApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootAdjunctApplication.class, args);
		//String s = MD5Utils.md5("2202104845"+"9d0f0fa6b570402985d70fb5729ef58c");
		/*String a[]={"cedc3bbbc9ef4062a40d85de9a1030e8" ,
				"9803ebf14fae420d85dfb04203b69a61" ,
				"a57039165a614f7bbb6a7b254b269bc4" ,
				"57de131a1eef47d4991b04c5511972f9" ,
				"65a3b9ee97a3455e84abd84989906d2a" ,
				"65a3b9ee97a3455e84abd84989906d2a" ,
				"6f1a88c075974d1b9ce73d2ae0b46151" ,
				"6cf125a004c14ad286258c8a2c8af9ee" ,
				"01db78abe3644692960aeacf82de4f87" ,
				"496c965ec31349b1a4480b907f453d8d" ,
				"452509428390401dbaff65f42502f6bf" ,
				"003625fc94fe476686c563f765075f57" ,
				"d79c4e2015614c90ba4d34ae6bef310e" ,
				"82127de07cac42889415a3a5eca4e0d1" ,
				"805cbba7f98a4fc7ad523c856ab70894" ,
				"4cb20c9afd6a4b13854a07f7bd7a81c0"};
		for(String i:a){
			String s = MD5Utils.md5("2202104845"+i);
			System.out.println(s);
		}
*/
	}

}
