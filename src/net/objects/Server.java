package net.objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "server")
public class Server {
	private int id;
	private String hostname;
	private int port;
	private String name;
	private short channelType;
	private short serverIndex;
	private int population;
	private int maxPopulation;
	private String bestGuild;
	
	public Server() {

	}

	public Server(int id, String hostname, int port, String name, short channelType, short serverIndex, int population, int maxPopulation) {
		this.id = id;
		this.hostname = hostname;
		this.port = port;
		this.name = name;
		this.channelType = channelType;
		this.serverIndex = serverIndex;
		this.population = population;
		this.maxPopulation = maxPopulation;
	}

	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name = "server_id")
	public int getId() {
		return id;
	}

	@Column(name = "hostname")
	public String getHostname() {
		return hostname;
	}

	@Column(name = "port")
	public int getPort() {
		return port;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	@Column(name = "channel_type")
	public short getChannelType() {
		return channelType;
	}

	@Column(name = "server_index")
	public short getServerIndex() {
		return serverIndex;
	}

	@Column(name = "population")
	public int getPopulation() {
		return population;
	}

	@Column(name = "max_population")
	public int getMaxPopulation() {
		return maxPopulation;
	}
	
	@Transient
	public String getBestGuild() {
		return bestGuild;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setChannelType(short channelType) {
		this.channelType = channelType;
	}

	public void setServerIndex(short serverIndex) {
		this.serverIndex = serverIndex;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public void setMaxPopulation(int maxPopulation) {
		this.maxPopulation = maxPopulation;
	}
	
	public void setBestGuild(String bestGuild) {
		this.bestGuild = bestGuild;
	}
}
