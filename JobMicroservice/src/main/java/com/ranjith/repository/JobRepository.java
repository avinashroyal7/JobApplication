package com.ranjith.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ranjith.entity.Job;

public interface JobRepository extends JpaRepository<Job,Long>{

}
