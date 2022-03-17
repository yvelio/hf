INSERT INTO history(history_id, fileName) VALUES(1, 'HH20210217 Chimaera V - $0.01-$0.02 - USD No Limit Holdem.txt');
INSERT INTO history(history_id, fileName) VALUES(2, 'HH20201111 Clematis - $0.01-$0.02 - USD No Limit Holdem.txt');


INSERT INTO hand(hand_id, handNumber, tableName, history_id) VALUES (1, 223914234478, 'Chimaera V', 1);
INSERT INTO hand(hand_id, handNumber, tableName, history_id) VALUES (2, 223914243616, 'Chimaera V', 1);
INSERT INTO hand(hand_id, handNumber, tableName, history_id) VALUES (3, 220273645838, 'Clematis', 2);


INSERT INTO player(player_id, playerName, site, hand_id) VALUES (1, 'makkrik', 1, 1);
INSERT INTO player(player_id, playerName, site, hand_id) VALUES (2, 'yvel310', 1, 1);
INSERT INTO player(player_id, playerName, site, hand_id) VALUES (3, 'Ducker0', 1, 1);
INSERT INTO player(player_id, playerName, site, hand_id) VALUES (4, 'Dream ohm', 1, 1);

INSERT INTO player(player_id, playerName, site, hand_id) VALUES (5, 'makkrik', 1, 2);
INSERT INTO player(player_id, playerName, site, hand_id) VALUES (6, 'yvel310', 1, 2);
INSERT INTO player(player_id, playerName, site, hand_id) VALUES (7, 'Ducker0', 1, 2);
INSERT INTO player(player_id, playerName, site, hand_id) VALUES (8, 'Dream ohm', 1, 2);


INSERT INTO player(player_id, playerName, site, hand_id) VALUES (9, 'Azznak', 1, 3);
INSERT INTO player(player_id, playerName, site, hand_id) VALUES (10, 'sign514', 1, 3);
INSERT INTO player(player_id, playerName, site, hand_id) VALUES (11, 'MAMUKA_AKQJT', 1, 3);
INSERT INTO player(player_id, playerName, site, hand_id) VALUES (12, 'yvel310', 1, 3);


INSERT INTO hero(hero_id,  hand_id, player_id) VALUES (1, 1, 2);
INSERT INTO hero(hero_id,  hand_id, player_id) VALUES (2, 2, 6);
INSERT INTO hero(hero_id,  hand_id, player_id) VALUES (3, 3, 12);

