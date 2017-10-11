
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.RaffleRepository;

@Service
@Transactional
public class RaffleService {

	@Autowired
	private RaffleRepository	raffleRepository;
}
