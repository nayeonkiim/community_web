package me.mykite.spring.userVO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsVO implements UserDetails{
	
	private String username;
	private String password;
	private List<GrantedAuthority> authorities;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<GrantedAuthority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(List<String> authList) {
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for(int i = 0; i < authList.size(); i++) {
			authorities.add(new SimpleGrantedAuthority(authList.get(i)));
		}
		
		this.authorities = authorities;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}
