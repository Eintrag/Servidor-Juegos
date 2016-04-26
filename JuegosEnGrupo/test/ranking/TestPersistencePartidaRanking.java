package ranking;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.uclm.esi.common.server.domain.RankingEntry;
import edu.uclm.esi.common.server.persistence.BrokerWithRankingDB;
import edu.uclm.esi.common.server.persistence.DAOPartida;
import edu.uclm.esi.common.server.persistence.DAOPartidaForTest;

public class TestPersistencePartidaRanking {
	
	@Before
	@After
	public void setup() throws Exception {
		DAOPartidaForTest.dropTable();
		DAOPartidaForTest.createTable();
	}

	@Test
	public void testRankingBD() throws Exception {
		Connection bd = BrokerWithRankingDB.get().getRankingDB();
		assertNotNull(bd);
	}

	@Test
	public void testInsertPartida() throws Exception {
		DAOPartidaForTest.addPartida("test@insert.com", "insert@test.com", 20);
		DAOPartidaForTest.addPartida("test@insert.com", "insert@test.com", 30);
		DAOPartidaForTest.addPartida("insert@test.com", "test@insert.com", 40);
	}

	@Test
	public void testGetVictorias() throws Exception {
		DAOPartidaForTest.addPartida("test@insert.com", "insert@test.com", 20);
		DAOPartidaForTest.addPartida("test@insert.com", "insert@test.com", 30);
		DAOPartidaForTest.addPartida("insert@test.com", "test@insert.com", 40);
		
		assertEquals(0, DAOPartidaForTest.getVictorias("aaaa"));
		assertEquals(2, DAOPartidaForTest.getVictorias("test@insert.com"));
		assertEquals(1, DAOPartidaForTest.getVictorias("insert@test.com"));
	}

	@Test
	public void testGetTop10() throws Exception {
		DAOPartidaForTest.addPartida("test@insert.com", "insert@test.com", 20);
		DAOPartidaForTest.addPartida("test@insert.com", "insert@test.com", 30);
		DAOPartidaForTest.addPartida("insert@test.com", "test@insert.com", 40);
		
		List<RankingEntry> rankingEntries = DAOPartida.getTopTen().getRankingEntries();
		assertTrue(rankingEntries.size() < 10);
		assertEquals("test@insert.com", rankingEntries.get(0).getEmailganador());
		assertEquals(2, rankingEntries.get(0).getNumVictorias());
		assertEquals("insert@test.com", rankingEntries.get(1).getEmailganador());
		assertEquals(1, rankingEntries.get(1).getNumVictorias());
	}
}
