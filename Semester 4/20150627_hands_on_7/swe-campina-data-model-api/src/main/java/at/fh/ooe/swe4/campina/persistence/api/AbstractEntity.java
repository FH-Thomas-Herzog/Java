package at.fh.ooe.swe4.campina.persistence.api;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

public abstract class AbstractEntity implements Serializable {

	private static final long	serialVersionUID	= 1095329951571671581L;

	private Integer				id;
	private Long				version;

	public AbstractEntity() {
	}

	public AbstractEntity(Integer id) {
		super();
		this.id = id;
	}

	@Id
	@Column(name = "ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "version")
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractEntity other = (AbstractEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
