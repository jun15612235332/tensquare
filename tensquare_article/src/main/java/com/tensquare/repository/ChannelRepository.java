package com.tensquare.repository;

import com.tensquare.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ChannelRepository extends JpaRepository<Channel,String>,JpaSpecificationExecutor<Channel>{
}
