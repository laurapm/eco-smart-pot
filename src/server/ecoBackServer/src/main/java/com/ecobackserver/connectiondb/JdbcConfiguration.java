package com.ecobackserver.connectiondb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcConfiguration 
{
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	Collection<Map<String, Object>> rows = jdbc.queryForList("SELECT QUERY");
}
