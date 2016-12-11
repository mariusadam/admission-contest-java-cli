package com.ubb.map.repository.qualifiers;

import javax.inject.Qualifier;
import javax.inject.Singleton;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Qualifier
@Singleton
public @interface CandidateRepo {}