package com.citsh.dao.jpa;

import java.io.Serializable;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import com.citsh.dao.jpa.impl.BaseDaoImpl;

public class BaseDaoFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable>
		extends JpaRepositoryFactoryBean<R, T, I> {
	/**
	 * 接到factory之后，把factory扔了spring data jpa
	 */
	@Override
	protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {
		return new BaseRepositoryFactory(em);
	}

	/**
	 * //创建一个内部类，该类不用在外部访问 他的作用是将我们的baseReposity的实现类扔给factorybean
	 *
	 * @param <T>
	 * @param <I>
	 */

	private static class BaseRepositoryFactory<T, I extends Serializable> extends JpaRepositoryFactory {

		private final EntityManager em;

		public BaseRepositoryFactory(EntityManager em) {
			super(em);
			this.em = em;
		}

		/**
		 * 通过这两个方法来确定具体的实现类，也就是Spring Data Jpa具体实例化一个接口的时候会去创建的实现类。
		 */
		// 设置具体的实现类是BaseRepositoryImpl
		protected Object getTargetRepository(RepositoryInformation information) {
			return new BaseDaoImpl<T, I>((Class<T>) information.getDomainType(), em);
		}

		// 设置具体的实现类的class
		protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
			return BaseDaoImpl.class;
		}
	}
}