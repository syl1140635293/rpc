package com.syl.rpc.interfacce;



import com.syl.rpc.config.MiniRpcAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Import(MiniRpcAutoConfiguration.class)
public @interface EnableMiniRpc {
}
