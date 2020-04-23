package com.practice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class Stock {
	
	@Id
	private String date;
	private String company;
	private Float open;
	private Float close;
	private Float high;
	private Float low;
	private Integer volume;
	private String profession;
	private String sector;
	private String address;
	private String registration;
	
}
