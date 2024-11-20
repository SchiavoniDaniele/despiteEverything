package utils;

import java.util.Random;

import main.dao.BookDAO;
import main.dao.ScepterDAO;
import main.dao.WeaponDAO;
import main.models.Book;
import main.models.Sayre;
import main.models.Scepter;
import main.models.Weapon;

public class UtensilUtils {
	private static Random rand=new Random();
	
	private static final String[] level1WeaponNames = { "Spada di Ruggine", "Daga del Principiante", "Lancia d'Acciaio",
			"Mazza del Contadino", "Lama di Pietra", "Pugnale dell'Apprendista", "Martello di Legno", "Spada dei Campi",
			"Ascia del Taglialegna", "Spadone di Ferro Grezzo", "Tridente di Rame", "Lama del Ventaglio",
			"Falce del Falciatore", "Scudo di Bronzo", "Spada del Ramingo", "Pugnale di Ferro", "Mazza del Nomade",
			"Lancia della Collina", "Spada del Rovere", "Daga della Nebbia", "Falce del Raccoglitore",
			"Lama del Viandante", "Ascia dell'Iniziato", "Lancia di Rame", "Scimitarra di Sabbia", "Stocco del Predone",
			"Spada del Ruggine", "Pugnale del Ladro", "Spada di Bronzo", "Tridente della Marea", "Falcetto del Fabbro",
			"Mazza della Terra", "Lama di Fuoco", "Spada delle Ombre", "Pugnale del Cacciatore", "Lancia di Legno",
			"Spada del Fabbro", "Daga del Buio", "Spada della Tempesta", "Ascia del Falò", "Mazza di Cenere",
			"Spada del Deserto", "Falcetto di Pietra", "Pugnale del Bosco", "Martello dell'Artigiano", "Lama del Sogno",
			"Ascia del Cacciatore", "Spada di Ferro", "Pugnale dell'Esploratore", "Lancia del Vento",
			"Stocco della Notte", "Mazza del Tuono", "Spada del Vento", "Daga del Viaggiatore", "Martello del Minatore",
			"Spada del Villaggio", "Lama della Foresta", "Scudo del Ferro", "Pugnale delle Ombre", "Ascia del Pastore",
			"Spadone del Ferrovecchio", "Mazza del Vagabondo", "Falcetto del Coltivatore", "Spada del Sole",
			"Tridente del Naufrago", "Pugnale del Cacciatore Notturno", "Lancia della Rovina", "Martello di Bronzo",
			"Lama della Bruma", "Scudo del Deserto", "Ascia della Valle", "Daga del Mercenario", "Lancia della Pietra",
			"Stocco del Guardaboschi", "Spada della Palude", "Pugnale del Ladro Silenzioso", "Mazza della Polvere",
			"Lama del Vagabondo", "Scudo della Roccia", "Ascia del Predone", "Pugnale della Luna",
			"Lancia del Viaggiatore", "Falce del Bosco", "Spada del Cielo", "Martello del Fabbricante",
			"Daga del Sentiero", "Spada della Caccia", "Pugnale dell'Alba", "Lancia del Mare", "Mazza della Sabbia",
			"Falcetto della Montagna", "Spadone del Legno", "Pugnale del Sussurro", "Martello del Fuoco",
			"Lama del Ghiaccio", "Daga dell'Antico", "Tridente del Mormorio", "Ascia dell'Onda", "Lancia della Pioggia",
			"Mazza del Sole", "Stocco dell'Antico", "Spada della Nebbia" };
	
	private static final String[] level2WeaponNames = { "Spada del Tramonto", "Daga della Tempesta",
			"Lancia del Cacciatore", "Mazza di Ferro Forgiato", "Lama del Fulmine", "Pugnale d'Argento",
			"Martello del Titano", "Spada dell'Orizzonte", "Ascia del Sanguinario", "Spadone della Gloria",
			"Tridente delle Maree", "Lama dell'Esploratore", "Falce del Giudice", "Scudo della Fortezza",
			"Spada del Vento Tagliente", "Pugnale del Rapace", "Mazza del Guerriero", "Lancia del Dragone",
			"Spada della Quercia", "Daga del Teschio", "Falce della Mietitura", "Lama dell'Anima", "Ascia della Lince",
			"Lancia del Guardiano", "Scimitarra del Deserto Rosso", "Stocco del Maestro", "Spada del Sangue Antico",
			"Pugnale del Sole", "Spada della Luna", "Tridente dell'Oceano Profondo", "Falcetto del Fato",
			"Mazza delle Radici", "Lama di Fiamma Azzurra", "Spada del Sussurro", "Pugnale della Fiamma",
			"Lancia di Quarzo", "Spada dell'Alba", "Daga dell'Eclissi", "Spada del Guerriero",
			"Ascia del Vento Selvaggio", "Mazza del Predatore", "Spada della Sabbia", "Falcetto del Fuoco",
			"Pugnale della Notte", "Martello del Gigante", "Lama del Corvo", "Ascia della Tempesta",
			"Spada del Grifone", "Pugnale del Lupo", "Lancia dell'Aquila", "Stocco della Lama Segreta",
			"Mazza del Crepuscolo", "Spada del Fulmine Blu", "Daga del Fabbro Celeste", "Martello dell'Alba",
			"Spada del Ramingo", "Lama dell'Oscurità", "Scudo dell'Equilibrio", "Pugnale del Veleno",
			"Ascia del Cacciatore di Anime", "Spadone dell'Ombra", "Mazza del Guardaboschi",
			"Falcetto della Notte Stellata", "Spada del Crepuscolo", "Tridente dell'Abisso", "Pugnale del Giuramento",
			"Lancia del Toro", "Martello di Tempesta", "Lama della Nebbia", "Scudo delle Stelle",
			"Ascia della Montagna", "Daga del Serpente", "Lancia della Rovina Dorata", "Stocco dell'Aquila Reale",
			"Spada dell'Incubo", "Pugnale del Falco", "Mazza della Fenice", "Lama della Foresta Oscura",
			"Scudo dell'Ombra", "Ascia del Lupo Feroce", "Pugnale della Tempesta Blu", "Lancia del Mago",
			"Falce del Grano Nero", "Spada della Giustizia", "Martello del Guardiano", "Daga della Montagna",
			"Spada della Tempesta Divina", "Pugnale della Luce", "Lancia della Rovina Antica",
			"Mazza del Deserto Dorato", "Falcetto della Luna Piena", "Spadone del Sole", "Pugnale della Furia",
			"Martello dell'Oracolo", "Lama dell'Eterno", "Daga del Guerriero Notturno", "Tridente del Giuramento",
			"Ascia del Risveglio", "Lancia della Fenice", "Mazza della Rinascita", "Stocco della Gloria Antica",
			"Spada della Luce" };
	
	private final static String[] level3WeaponNames = { "Spada del Divoratore di Mondi", "Daga della Notte Eterna",
			"Lancia del Drago Antico", "Mazza della Furia Celeste", "Lama del Sole Infranto",
			"Pugnale dell'Oscurità Primordiale", "Martello dell'Apocalisse", "Spada dell'Infinito", "Ascia del Destino",
			"Spadone del Dominatore", "Tridente dell'Abisso Profondo", "Lama dell'Essenza Arcana",
			"Falce della Mietitura Finale", "Scudo dell'Eternità", "Spada della Tempesta Infernale",
			"Pugnale della Vendetta Immortale", "Mazza del Signore delle Tempeste", "Lancia della Fenice Fiammeggiante",
			"Spada della Quercia Sacra", "Daga del Giudizio Finale", "Falce del Guardiano Celeste",
			"Lama dell'Anima Perduta", "Ascia del Signore dei Mari", "Lancia del Custode delle Stelle",
			"Scimitarra del Re degli Inferi", "Stocco della Lama Celestiale", "Spada del Sangue dei Titani",
			"Pugnale della Lama Divina", "Spada della Luna Rossa", "Tridente dell'Oceano Sconfinato",
			"Falcetto dell'Eclissi Nera", "Mazza delle Radici della Terra", "Lama del Fuoco Eternamente Ardente",
			"Spada del Sussurro dell'Universo", "Pugnale del Crepuscolo Infinito", "Lancia della Stella Cadente",
			"Spada dell'Aurora Dorata", "Daga della Regina delle Ombre", "Spada del Dominio Assoluto",
			"Ascia del Conquistatore di Mondi", "Mazza del Guardiano Celestiale", "Spada della Sabbia Eterna",
			"Falcetto del Rogo Finale", "Pugnale dell'Essenza Oscura", "Martello del Titano di Pietra",
			"Lama dell'Aquila Divina", "Ascia della Tempesta Scatenata", "Spada del Grifone Leggendario",
			"Pugnale della Luna Sanguinaria", "Lancia dell'Aquila Reale", "Stocco della Luce Sacra",
			"Mazza del Destino Sigillato", "Spada del Fulmine Nero", "Daga dell'Eterno Fabbro",
			"Martello del Risveglio Celeste", "Spada del Guardiano dell'Universo", "Lama della Fine dei Tempi",
			"Scudo dell'Equilibrio Cosmico", "Pugnale dell'Oscurità Perpetua", "Ascia della Furia degli Spiriti",
			"Spadone del Destino Inarrestabile", "Mazza del Cuore della Foresta", "Falce della Notte Infinita",
			"Spada dell'Alba Infuocata", "Tridente del Leviatano", "Pugnale del Giuramento Oscuro",
			"Lancia del Toro Immortale", "Martello della Tempesta Sacra", "Lama della Nebbia Arcana",
			"Scudo della Furia delle Stelle", "Ascia della Montagna Divina", "Daga del Serpente degli Abissi",
			"Lancia della Profezia Perduta", "Stocco dell'Aquila Eterna", "Spada del Re degli Incubi",
			"Pugnale dell'Aquila Dorata", "Mazza della Fenice Rinata", "Lama della Foresta delle Ombre",
			"Scudo dell'Arcano", "Ascia del Lupo degli Spiriti", "Pugnale della Tempesta Celeste",
			"Lancia del Mago Supremo", "Falce della Mietitura Oscura", "Spada della Giustizia Divina",
			"Martello del Guardiano Eterno", "Daga della Montagna dei Giganti", "Spada della Tempesta Eterna",
			"Pugnale della Luce Infinita", "Lancia della Divina Rovina", "Mazza del Sole Splendente",
			"Falcetto della Luna Bloccata", "Spadone della Luce Radiosa", "Pugnale della Furia Ardente",
			"Martello dell'Oracolo Supremo", "Lama dell'Immortalità", "Daga del Guerriero Celeste",
			"Tridente del Giudizio Universale", "Ascia del Risveglio della Terra", "Lancia della Fiamma Eterna",
			"Mazza della Rinascita Divina", "Stocco della Gloria dei Santi", "Spada della Luce Eterna" };
	
	private final static String[] level1SceptreNames = { "Scettro dei Sogni Nebulosi", "Scettro del Sussurro Arcano",
			"Scettro della Notte Quietante", "Scettro dell’Illusione", "Scettro della Nebbia Lunare",
			"Scettro del Silenzio Soave", "Scettro dei Sogni Velati", "Scettro della Nebbia Rilassante",
			"Scettro del Riposo Arcano", "Scettro della Brezza Ipnotica", "Scettro della Nebbia Illusoria",
			"Scettro delle Visioni Leggere", "Scettro dei Sogni Svanenti", "Scettro dell’Oscurità Tranquilla",
			"Scettro del Canto Quietante", "Scettro della Luce Soporifera", "Scettro delle Ombre Soffuse",
			"Scettro dell'Incanto Leggero", "Scettro dei Miraggi Nebulosi", "Scettro della Serenità Nebbiosa",
			"Scettro delle Nebbie Ipnotiche", "Scettro della Nebbia Sospesa", "Scettro della Quiete Profonda",
			"Scettro del Sogno Dolcemente Stregato", "Scettro dell’Incanto Vago", "Scettro del Vento Soave",
			"Scettro delle Illusioni di Luna", "Scettro del Crepuscolo Ipnotico", "Scettro della Sospensione",
			"Scettro dell’Oblio Dolce", "Scettro delle Ombre Seducenti", "Scettro delle Stelle Ipnotiche",
			"Scettro della Sospensione Illusoria", "Scettro dell'Allucinazione", "Scettro della Rugiada Ipnotica",
			"Scettro della Foschia Magica", "Scettro del Torpore Celeste", "Scettro del Canto Incantatore",
			"Scettro della Luce Soffusa", "Scettro della Tranquillità Arcana", "Scettro della Foschia Luminosa",
			"Scettro dei Sogni Effimeri", "Scettro della Brezza Sognante", "Scettro del Sussurro Lunare",
			"Scettro della Nebbia Calma", "Scettro dell’Abisso Soave", "Scettro della Quiete Ipnotica",
			"Scettro del Crepuscolo Magico", "Scettro della Luce Incantata", "Scettro della Foschia Tranquilla",
			"Scettro dell’Eclissi Ipnotica", "Scettro del Velo Lunare", "Scettro del Sogno Breve",
			"Scettro del Respiro Incantatore", "Scettro della Serenità Arcana", "Scettro della Notte Soffusa",
			"Scettro della Foschia d’Incanto", "Scettro del Respiro Leggero", "Scettro della Luna Ipnotica",
			"Scettro delle Ombre Lente", "Scettro della Visione Calma", "Scettro del Canto Incantato",
			"Scettro della Rugiada Ipnotica", "Scettro del Vento Magico", "Scettro della Luce dei Sogni",
			"Scettro delle Stelle Sfumate", "Scettro della Foschia Ipnotica", "Scettro della Visione Serale",
			"Scettro della Serenità Notturna", "Scettro della Luce dei Ricordi", "Scettro del Crepuscolo Quieto",
			"Scettro della Nebbia Ipnotizzante", "Scettro del Canto dell’Alba", "Scettro dell’Incanto Tenue",
			"Scettro del Riposo Lunare", "Scettro della Quiete Notturna", "Scettro della Nebbia Soave",
			"Scettro del Respiro Nebuloso", "Scettro della Luce Sognante", "Scettro dell’Oblio Lieve",
			"Scettro della Foschia Ipnotica", "Scettro della Soglia Incantata", "Scettro delle Stelle Quietanti",
			"Scettro del Sonno Magico", "Scettro delle Visioni Sfuggenti", "Scettro della Brezza Quietante",
			"Scettro della Luce Ipnotica", "Scettro del Tramonto Magico", "Scettro delle Ombre Nebulose",
			"Scettro della Sospensione Soave", "Scettro del Velo dei Sogni", "Scettro della Luna Soffusa",
			"Scettro della Visione Ipnotica", "Scettro del Respiro Lontano", "Scettro della Luce Ipnotizzante",
			"Scettro del Sogno Oscuro", "Scettro della Foschia Serenante", "Scettro della Notte Incantata",
			"Scettro della Sospensione dei Sogni", "Scettro della Luce Lunare", "Scettro del Canto Ipnotico",
			"Scettro del Sonno Quieto", "Scettro della Quiete Lunare", "Scettro dell’Incanto della Foschia",
			"Scettro delle Ombre dei Sogni" };

	private final static String[] level2SceptreNames = { "Scettro dell’Occhio Ipnotico", "Scettro del Sogno Profondo",
			"Scettro dell’Abbraccio Oscuro", "Scettro della Trance Lunare", "Scettro della Foschia Celeste",
			"Scettro della Nebbia Oscura", "Scettro del Richiamo Ipnotico", "Scettro della Visione Interiore",
			"Scettro del Crepuscolo Ipnotico", "Scettro del Velo Illusorio", "Scettro della Pace Sospesa",
			"Scettro dell’Incanto Insondabile", "Scettro dell’Oblio Profondo", "Scettro della Serenità Mistica",
			"Scettro del Sogno Vorticoso", "Scettro del Respiro Soporifero", "Scettro dell’Ipnosi Notturna",
			"Scettro delle Nebbie di Sogno", "Scettro dell’Abisso Illusorio", "Scettro della Luce Ipnotizzante",
			"Scettro del Respiro Profondo", "Scettro delle Visioni Infinite", "Scettro del Canto Stregato",
			"Scettro del Torpore Arcano", "Scettro della Nebbia Fatata", "Scettro della Tranquillità Lunare",
			"Scettro della Luce Ipnotica", "Scettro delle Ombre Stregate", "Scettro della Calma Astrale",
			"Scettro del Crepuscolo Sospeso", "Scettro della Foschia Enigmatica", "Scettro della Luce del Sonno",
			"Scettro della Sfera Ipnotica", "Scettro della Serenità Sommersa", "Scettro del Vento di Sogno",
			"Scettro dell’Essenza Ipnotica", "Scettro del Canto Eterno", "Scettro dell’Illusione Profonda",
			"Scettro del Riposo Magico", "Scettro della Quiete Eterna", "Scettro della Luce Enigmatica",
			"Scettro delle Ombre dei Sogni", "Scettro dell’Allucinazione Celeste", "Scettro della Foschia Eterna",
			"Scettro dell’Occhio di Sogno", "Scettro del Silenzio Arcano", "Scettro della Visione Notturna",
			"Scettro della Quiete Suprema", "Scettro della Nebbia Ipnotizzante", "Scettro del Torpore Sospeso",
			"Scettro delle Stelle Sognanti", "Scettro del Tramonto Incantato", "Scettro del Richiamo Onirico",
			"Scettro della Foschia Stellare", "Scettro della Brezza Ipnotica", "Scettro della Sospensione Arcana",
			"Scettro della Visione Fantasmatica", "Scettro della Foschia dell’Oblio", "Scettro della Nebbia Incantata",
			"Scettro del Sogno Dorato", "Scettro della Foschia Notturna", "Scettro del Sogno dei Mortali",
			"Scettro dell’Ipnosi Suprema", "Scettro della Trance dei Mari", "Scettro della Quiete Magica",
			"Scettro della Serenità Ipnotizzante", "Scettro del Vento della Notte", "Scettro dell’Illusione Ipnotica",
			"Scettro della Visione Velata", "Scettro del Sogno Lontano", "Scettro del Canto di Ombre",
			"Scettro del Respiro dell’Essenza", "Scettro della Nebbia dei Miraggi", "Scettro della Sfera della Nebbia",
			"Scettro del Velo Arcano", "Scettro della Tranquillità Soave", "Scettro del Miraggio Ipnotico",
			"Scettro della Visione Rilassante", "Scettro della Luce di Sogno", "Scettro della Brezza Ipnotica",
			"Scettro della Notte Ipnotizzante", "Scettro dell’Incanto del Crepuscolo", "Scettro della Rugiada di Luce",
			"Scettro del Canto dell’Alba", "Scettro della Foschia dei Ricordi", "Scettro della Brezza Tranquillizzante",
			"Scettro dell’Oblio Lunare", "Scettro dell’Occhio della Nebbia", "Scettro dell’Essenza Notturna",
			"Scettro della Quiete dei Sogni", "Scettro della Luce della Luna", "Scettro dell’Ipnosi Celeste",
			"Scettro delle Ombre delle Stelle", "Scettro della Luce Serafica", "Scettro della Nebbia Oscura",
			"Scettro della Sospensione Soave", "Scettro dell’Abbraccio delle Nebbie", "Scettro della Foschia Soffusa",
			"Scettro della Trance Celestiale", "Scettro della Luce Lunare", "Scettro del Sogno dell’Eternità",
			"Scettro della Sfera dei Sogni", "Scettro della Serenità Sommersa", "Scettro della Foschia dei Sogni",
			"Scettro dell’Essenza Incantatrice" };

	private final static String[] level3SceptreNames = { "Scettro dell’Infinito Ipnotico",
			"Scettro dell’Abisso Mentale", "Scettro del Dominio Onirico", "Scettro dell’Oblio Soprannaturale",
			"Scettro della Trance Eterna", "Scettro delle Ombre Sublimi", "Scettro del Sogno Eterno",
			"Scettro della Luce Fantasmatica", "Scettro dell’Incanto Assoluto", "Scettro della Pace Oscura",
			"Scettro del Controllo Celestiale", "Scettro del Riposo Ipnotico", "Scettro della Foschia Infinita",
			"Scettro della Serenità Suprema", "Scettro dell’Occhio di Stelle", "Scettro della Visione Universale",
			"Scettro dell’Ipnosi Inesorabile", "Scettro della Nebbia Divina", "Scettro del Torpore Profondo",
			"Scettro dell’Incanto Immutabile", "Scettro della Foschia Incantatrice", "Scettro della Luce dei Miraggi",
			"Scettro del Sonno Eterno", "Scettro dell’Essenza Ipnotica", "Scettro della Quiete Celeste",
			"Scettro della Luce Sognante", "Scettro della Sospensione Cosmica", "Scettro del Sogno Arcano",
			"Scettro della Foschia Ipnotica", "Scettro dell’Oblio Celestiale", "Scettro del Vento del Silenzio",
			"Scettro delle Stelle Ipnotiche", "Scettro della Visione del Vuoto", "Scettro dell’Ipnosi Arcana",
			"Scettro dell’Essenza Suprema", "Scettro della Trance degli Antichi", "Scettro del Silenzio Universale",
			"Scettro della Quiete Illusoria", "Scettro del Torpore Cosmico", "Scettro della Brezza Stellare",
			"Scettro della Serenità delle Stelle", "Scettro della Foschia degli Spiriti",
			"Scettro dell’Occhio dell’Eternità", "Scettro del Crepuscolo Incantato", "Scettro della Rugiada Celeste",
			"Scettro della Visione Infinita", "Scettro della Sospensione Arcana", "Scettro del Miraggio degli Dei",
			"Scettro dell’Incanto della Luna", "Scettro della Foschia Misteriosa", "Scettro dell’Oblio degli Dei",
			"Scettro della Notte Assoluta", "Scettro del Sogno degli Antichi", "Scettro del Canto Ipnotico",
			"Scettro della Trance Suprema", "Scettro del Dominio del Vuoto", "Scettro della Luce degli Eterni",
			"Scettro dell’Oblio Incantato", "Scettro del Velo della Nebbia", "Scettro della Pace Illusoria",
			"Scettro del Dominio dell’Oblio", "Scettro della Nebbia Celestiale", "Scettro della Trance degli Spiriti",
			"Scettro della Quiete Universale", "Scettro della Luce Ipnotizzante", "Scettro della Foschia dell’Eternità",
			"Scettro della Serenità Arcana", "Scettro del Torpore degli Dei", "Scettro della Visione Ipnotica",
			"Scettro del Silenzio Ipnotico", "Scettro della Brezza Divina", "Scettro della Quiete Sospesa",
			"Scettro della Visione dei Mondi", "Scettro dell’Occhio di Stelle", "Scettro dell’Incanto Oscuro",
			"Scettro dell’Ipnosi Totale", "Scettro dell’Essenza degli Antichi", "Scettro del Velo Arcano",
			"Scettro della Trance Celestiale", "Scettro della Luce dei Misteri", "Scettro del Crepuscolo Ipnotico",
			"Scettro della Foschia Arcana", "Scettro della Quiete Suprema", "Scettro del Respiro degli Spiriti",
			"Scettro dell’Essenza Ipnotica", "Scettro del Richiamo Eterno", "Scettro della Foschia dei Mondi",
			"Scettro della Serenità Sospesa", "Scettro della Visione dell’Infinito", "Scettro del Torpore Divino",
			"Scettro della Luce degli Antichi", "Scettro dell’Ipnosi Cosmica", "Scettro dell’Essenza delle Stelle",
			"Scettro del Canto dell’Eternità", "Scettro dell’Incanto delle Ombre", "Scettro della Foschia dei Sogni",
			"Scettro della Visione Suprema", "Scettro della Tranquillità Assoluta", "Scettro dell’Oblio Assoluto",
			"Scettro della Brezza Onirica", "Scettro dell’Incanto Supremo", "Scettro della Serenità Celeste",
			"Scettro dell’Occhio degli Antichi", "Scettro della Luce Infinita", "Scettro del Sonno Cosmico" };

	private final static String[] level1Books = { "Introduzione alla Società Civile", "Manuale del Buon Cittadino",
			"Guida alla Vita Pacifica", "Elementi di Convivenza", "La Forza della Collaborazione",
			"Fondamenti di Rispetto", "Il Senso dell’Altruismo", "Primi Passi nella Civiltà",
			"Manuale delle Buone Maniere", "La Via della Pazienza", "Abc della Cittadinanza", "Guida alla Cooperazione",
			"L'Importanza del Rispetto", "Fondamenti di Empatia", "Il Valore della Generosità",
			"Elementi di Gentilezza", "Come Integrarsi in Comunità", "Introduzione alla Responsabilità",
			"Le Basi del Dialogo", "Come Convivere in Pace", "Manuale della Tranquillità", "Lezioni di Serenità",
			"Guida alla Vita in Comune", "Fondamenti di Pazienza", "Le Virtù della Tolleranza",
			"Il Potere della Pazienza", "La Base dell’Accoglienza", "Percorsi di Autocontrollo",
			"Elementi di Semplicità", "Introduzione alla Mansuetudine", "Compendio dell’Umiltà",
			"La Forza della Gentilezza", "Introduzione alla Cortesia", "Come Aiutare il Prossimo",
			"L'Essenza della Saggezza", "Guida alla Serenità", "Elementi di Obbedienza", "Manuale della Comprensione",
			"Come Ascoltare gli Altri", "Introduzione al Dialogo", "Fondamenti di Lealtà", "Compendio della Tolleranza",
			"Guida al Rispetto Reciproco", "Il Potere dell’Empatia", "Le Basi della Concordia",
			"Guida alla Non Violenza", "Manuale della Cooperazione", "L'Essenza della Semplicità",
			"Fondamenti di Educazione", "Come Essere Affidabili", "Introduzione alla Moderazione",
			"Compendio della Serenità", "L'Essenza dell’Uguaglianza", "Guida alla Fiducia", "Manuale di Sincerità",
			"Primi Passi nell’Amicizia", "Come Sostenere gli Altri", "Guida alla Comunione",
			"Compendio della Moderazione", "Elementi di Chiarezza", "Introduzione al Rispetto",
			"Fondamenti di Benevolenza", "Guida alla Lealtà", "Manuale di Concordia", "Compendio dell’Accoglienza",
			"Le Basi della Cortesia", "Guida all’Armistizio", "Il Potere della Compassione", "Fondamenti di Giustizia",
			"Compendio della Benevolenza", "Manuale della Comprensione", "L'Essenza della Gentilezza",
			"Introduzione alla Solidarietà", "Guida alla Felicità Comune", "Compendio dell’Uguaglianza",
			"Manuale della Mansuetudine", "Come Gestire le Emozioni", "Fondamenti di Saggezza", "Guida alla Temperanza",
			"Il Valore della Fratellanza", "Introduzione alla Fiducia", "Le Virtù della Lealtà", "Guida alla Saggezza",
			"Manuale dell’Onestà", "Compendio della Mitezza", "Elementi di Moderazione",
			"Introduzione alla Gratitudine", "Guida alla Collaborazione", "Fondamenti di Disciplina",
			"Come Essere Pazienti", "Compendio della Generosità", "Introduzione all’Umiltà", "Guida alla Vita Sociale",
			"Manuale di Armonia", "L'Essenza della Compassione", "Come Convivere Serenamente", "Guida alla Gentilezza",
			"Compendio della Pacatezza", "Manuale della Riservatezza", "Elementi di Compassione",
			"Introduzione alla Benevolenza", "Guida alla Pace", "Fondamenti di Rispetto", "Primi Passi nel Buonsenso",
			"Come Essere Migliori" };

	private final static String[] level2Books = { "Manuale del Costruttore", "Guida alla Falegnameria di Base",
			"Tessitura per Principianti", "L’Arte del Giardinaggio", "Tecniche di Cucina Pratica",
			"Nozioni di Base per la Cura degli Animali", "Introduzione alla Metallurgia", "L’Apprendista Fabbro",
			"Guida all’Agricoltura Sostenibile", "Primi Passi nella Sartoria", "Manuale del Contadino",
			"Guida alla Cucina Salutare", "Fondamenti di Idraulica", "Il Piccolo Muratore",
			"Elementi di Pittura e Decorazione", "Lavorazione della Pietra", "Guida alla Pesca Sostenibile",
			"Elementi di Tessitura Avanzata", "Introduzione alla Panificazione", "Tecniche di Saldatura",
			"Manuale del Calzolaio", "L’Apprendista Ceramista", "Guida all’Arte del Cucito",
			"Primi Passi nella Scultura", "Lavorazione del Legno per Principianti", "Tecniche di Coltivazione",
			"Il Manuale dell’Ortolano", "Guida alla Cura delle Piante", "Introduzione alla Ceramica",
			"Fondamenti di Giardinaggio", "L’Arte della Pasticceria", "Guida alla Conservazione degli Alimenti",
			"Elementi di Posa dei Mattoni", "Tecniche di Sarta", "Introduzione alla Vetreria",
			"Il Mestiere del Macellaio", "Guida alla Caccia Sostenibile", "Fondamenti di Apicoltura",
			"Il Piccolo Calzolaio", "Manuale di Allevamento di Bestiame", "Tecniche di Potatura",
			"Guida al Commercio Sostenibile", "L’Apprendista Carpentiere", "Primi Passi nella Pastorizia",
			"Fondamenti di Riforestazione", "Manuale del Minatore", "L’Arte del Ricamo", "Guida alla Vendemmia",
			"Tecniche di Conservazione della Carne", "Introduzione alla Colorazione dei Tessuti",
			"L’Apprendista Meccanico", "Manuale della Saldatura", "Tecniche di Tessitura Avanzata",
			"Elementi di Apicoltura", "Il Piccolo Ornitologo", "Fondamenti di Idraulica Avanzata",
			"Guida alla Distillazione", "Lavorare il Cuoio", "Manuale della Cesteria",
			"Tecniche di Costruzione in Pietra", "Introduzione all’Ebanisteria", "Guida alla Fornace e alla Cottura",
			"Fondamenti di Zootecnia", "L’Arte del Soffiatore di Vetro", "Primi Passi nella Sartoria",
			"Il Giardiniere Esperto", "Tecniche di Tappezzeria", "Fondamenti di Coltivazione del Vino",
			"Lavorazione del Metallo per Principianti", "Guida alla Ceramica Decorativa", "Elementi di Cucina Esotica",
			"Introduzione alla Concia delle Pelli", "Fondamenti di Chimica per Artigiani",
			"Tecniche di Cucito Avanzate", "L’Arte del Cacciatore", "Guida alla Messa in Sicurezza",
			"Manuale di Panificazione", "Fondamenti di Impiantistica", "Tecniche di Pittura Murale",
			"Guida alla Tintura Naturale", "Elementi di Cucina Professionale", "Lavorazione della Carta",
			"Introduzione alla Fermentazione", "Guida alla Raccolta dei Frutti", "Tecniche di Riparazione Meccanica",
			"Manuale dell’Orefice", "Primi Passi nella Chimica delle Erbe", "Lavorazione della Lana",
			"Fondamenti di Sarta", "Tecniche di Affumicatura", "Guida alla Salatura e Stagionatura",
			"L’Arte del Restauro", "Manuale di Ebanisteria Avanzata", "Fondamenti di Elettrotecnica",
			"Tecniche di Coltura Idroponica", "Il Manuale del Botanico", "Primi Passi nella Ceramica",
			"L’Arte del Coltivatore", "Tecniche di Fabbricazione delle Candele",
			"Guida alla Manutenzione degli Utensili", "Fondamenti di Ingegneria Agricola", "Lavorazione del Lino",
			"Tecniche di Cucina per Banchetti", "Introduzione alla Decorazione dei Metalli", "L’Arte del Rilegatore" };

	private final static String[] level3Books = { "Versi della Profondità Silente",
			"La Sfera e il Vuoto di Kalenterios", "Manuale di Astrologia Avanzata", "Pensieri di Ombra e Luce",
			"Il Ciclo della Vita e dell'Oblio", "Trattato di Alchimia Filosofica", "La Mente Speculare di Zalgron",
			"Poesie dalla Terra e dal Cielo", "Manuale della Simbologia Antica", "La Ricerca della Pace Interiore",
			"Pensieri sul Tempo e lo Spazio", "L’Infinito in Poesia", "Filosofia della Dualità di Merkios",
			"Manuale di Ingegneria Arcana", "La Danza delle Parole", "Ode alla Follia e al Genio",
			"Le Visioni di Alethar", "Teorie dell’Universo", "Poesie dello Spirito Sognante", "I Paradossi di Talmeon",
			"Manuale dell’Anatomia Celeste", "Il Cammino dei Mille Sensi", "Poesia di Fuoco e Neve",
			"Gli Aforismi di Selthros", "La Musica del Silenzio", "Teorie dell'Esistenza Eterna",
			"L’Arte dell’Empatia Profonda", "Trattato sui Confini dell’Anima", "Poesia e Sogni Infranti",
			"Manuale di Cosmologia", "Il Libro dell’Estasi di Phystron", "La Caducità del Momento",
			"Manuale di Etica Complessa", "Poesie del Mare Oscuro", "Riflessioni sull’Essere",
			"Sulle Radici della Compassione", "I Principi della Verità di Nymerios",
			"Manuale di Costruzioni Metafisiche", "Versi Sussurrati al Vento", "Il Significato del Vuoto",
			"Filosofia della Luce di Eldrion", "Manuale di Incantesimi Superiori", "La Saggezza dei Rami",
			"Poesie d’Ombra e Fiamme", "Sogni del Vagabondo Celeste", "Pensieri sull’Ignoto", "Il Libro degli Echi",
			"Manuale della Materia Astrale", "L’Armonia dei Contrasti", "Trattato sull’Essenza di Zaleios",
			"L'Arte della Sublimazione", "Manuale di Telepatia Teorica", "Filosofia della Creazione",
			"Odi dell'Invisibile", "Il Vuoto Assoluto", "Teorie di Kaelidos", "L’Arte del Silenzio",
			"Manuale di Fisica Arcana", "Poesie di Stelle Cadenti", "I Canti di Sogno e Memoria",
			"Il Paradosso della Vita di Melekon", "Manuale di Alchimia Spirituale", "La Via dell'Intuizione",
			"Filosofia del Ciclo Eterno", "Pensieri di un Ermetico", "Il Poema dell’Universo", "Manuale di Psicometria",
			"Saggi sull’Essere e l’Apparenza", "Pensieri d’Oltremare", "L’Arte della Divinazione",
			"Il Simbolo e la Sostanza di Yralys", "Manuale di Bioalchimia", "La Metafora della Caduta",
			"Poesie di Sabbia e Vento", "I Varchi dell’Introspezione", "Dialoghi sull’Immateriale",
			"La Filosofia del Caos", "Manuale di Sigillografia", "Filosofia della Rinascita di Pthalon",
			"L’Essenza dell’Inesistenza", "Poesie del Desiderio e della Perdita", "Il Cammino del Silenzio Interiore",
			"Manuale di Enigmistica Avanzata", "Il Ciclo delle Ombre", "Riflessioni sulla Totalità",
			"La Scienza dell’Anima", "Pensieri su Ciò che Non È", "Odi alla Contemplazione",
			"Manuale della Trasmutazione Mentale", "I Fondamenti della Saggezza", "L’Infinito Nascosto di Torius",
			"L’Arte della Visione Interiore", "La Filosofia dei Miraggi", "Manuale di Empatia Universale",
			"Il Poema delle Anime Erranti", "Il Paradosso della Ragione di Melkris", "Trattato sull’Unità",
			"Le Risonanze dell’Io", "Pensieri sull’Essenza Ultima", "Il Libro del Vuoto Ricolmo",
			"Manuale di Telecinesi Applicata", "Odi di Sogno e Silenzio", "Il Canto dell’Anima Ribelle",
			"Filosofia della Luce e Ombra di Zalros", "Manuale di Proiezione Astrale", "Il Silenzio degli Antichi",
			"Trattato sulla Coscienza di Hyther", "Manuale della Sintonia Interiore" };

	private final static String[] rehabNames = {
		    "Rifugio della Bestia", "Oasi dei Mostri", "Rinnovamento Mostruoso", "Santuario delle Creature", 
		    "Centro Cura e Calma", "Casa dei Giganti Gentili", "Salvezza dei Demoni", "Luna Serenità", 
		    "Serenità della Foresta", "Rifugio della Caverna Oscura", "Riposo del Drago", "Oasi delle Ombre", 
		    "Centro del Risveglio", "Speranza dei Titani", "Rinascita della Bestia", "Grotta della Guarigione", 
		    "Isola dei Mostri Pacifici", "Sanctum dei Mostri", "Casa della Pace Oscura", "Centro della Luna Blu", 
		    "Rifugio di Rinnovamento", "Centro dell'Armonia", "Fortezza della Tranquillità", "Recupero delle Bestie", 
		    "Rinascita della Creatura", "Rifugio delle Ombre Gentili", "Rinascita del Gigante", "Rifugio dei Titani", 
		    "Lago dei Mostri Serafici", "Oasi del Cuore Oscuro", "Focolare dei Mostri", "Villa dei Colossi", 
		    "Pace dei Draghi", "Cura dei Mostri Perduti", "Residenza della Rinascita", "Centro dell'Equilibrio", 
		    "Cure del Vuoto", "Serenità del Mostro", "Armonia dei Golem", "Giardino delle Anime Mostruose", 
		    "Rifugio della Pace Notturna", "Rifugio delle Rocce", "Rinascita delle Ombre", "Serenità dei Guardiani", 
		    "Cura delle Creature Magiche", "Centro della Runa Curativa", "Santuario dell'Ultimo Sospiro", 
		    "Rinnovamento delle Anime", "Rifugio della Bestia Celeste", "Grotta delle Rinascite", "Sanctum dei Colossi", 
		    "Cura degli Spiriti Feriti", "Rinascita del Dragone", "Tana della Serenità", "Santuario delle Creature", 
		    "Rifugio degli Eoni", "Centro di Restauro", "Rifugio del Drago", "Centro della Luce Soffusa", 
		    "Rifugio della Notte Serenata", "Armonia del Crepuscolo", "Casa del Mostro Pacifico", "Grotta del Riposo", 
		    "Centro del Giorno e della Notte", "Cura dei Grandi Spiriti", "Rifugio della Luna Piena", 
		    "Centro della Tempesta Placata", "Grotta dell'Equilibrio", "Santuario dei Guardiani", "Dimora dei Mostri Calmi", 
		    "Centro delle Ombre Gentili", "Casa del Drago Sopito", "Isola della Guarigione", "Torre della Luna", 
		    "Centro dell'Oscuro Sollievo", "Rinascita del Colosso", "Centro del Sonno Eterno", "Rifugio della Notte Tranquilla", 
		    "Santuario delle Bestie", "Tempio del Mostro", "Centro del Cuore Selvaggio", "Oasi delle Anime Inquiete", 
		    "Rifugio dei Figli della Notte", "Centro delle Ombre", "Caverna della Riconciliazione", 
		    "Centro della Furia Placata", "Isola del Mostro Rinnovato", "Santuario del Ruggito Silente", 
		    "Serenità dei Mostri Ribelli", "Rifugio della Foresta Notturna", "Tana della Tempesta Calma", 
		    "Centro di Luce Oscura", "Villa della Pace Mostruosa", "Isola del Silenzio Sacro", "Grotta del Risveglio",
		    "Rifugio dell'Anima Errante", "Rinascita dell'Ombra", "Tana della Quiete", "Centro della Nebbia", 
		    "Santuario dei Mostri Celesti", "Rifugio dell'Abisso", "Rinascita delle Anime Perdute", 
		    "Cure dei Mostri Dimenticati", "Rinnovamento delle Creature"
		};

	
	public static int getWeaponPrice(int lvl) {
		//da sistemare, per adesso la mettiamo così
		int randomInt=rand.nextInt(100-50+1)+50;
		return randomInt*lvl;
	}
	public static String getWeaponName(int lvl) {
		switch(lvl) {
		case 1: return level1WeaponNames[rand.nextInt(level1WeaponNames.length)];
		case 2: return level2WeaponNames[rand.nextInt(level2WeaponNames.length)];
		case 3: return level3WeaponNames[rand.nextInt(level3WeaponNames.length)];
		default: return "Spada di gomma";
		}
	}
	public static int getWeaponDurability(int lvl) {
		//anche qui da sistemare dopo aver testato
		int randomInt=rand.nextInt(10-5+1)+5;
		return randomInt*lvl;
	}
	
	public static int getScepterPrice(int lvl) {
		//da sistemare, per adesso la mettiamo così
		int randomInt=rand.nextInt(100-50+1)+50;
		return randomInt*lvl;
	}
	public static String getScepterName(int lvl) {
		switch(lvl) {
		case 1: return level1SceptreNames[rand.nextInt(level1SceptreNames.length)];
		case 2: return level2SceptreNames[rand.nextInt(level2SceptreNames.length)];
		case 3: return level3SceptreNames[rand.nextInt(level3SceptreNames.length)];
		default: return "Scettro di legno marcio";
		}
	}
	public static int getScepterPower(int lvl) {
		//anche qui da sistemare dopo aver testato
		int randomInt=rand.nextInt(50-20+1)+50;
		return randomInt*lvl;
	}
	public static int getBookPrice(int lvl) {
		//da sistemare, per adesso la mettiamo così
		int randomInt=rand.nextInt(100-50+1)+50;
		return randomInt*lvl;
	}
	public static String getBookName(int lvl) {
		switch(lvl) {
		case 1: return level1Books[rand.nextInt(level1Books.length)];
		case 2: return level2Books[rand.nextInt(level2Books.length)];
		case 3: return level3Books[rand.nextInt(level3Books.length)];
		default: return "Libro da colorare";
		}
	}
	public static String getRehabName() {
		return rehabNames[rand.nextInt(rehabNames.length)];
	}
	public static Sayre setDafaultUtensils(Sayre sayre) {
		WeaponDAO weaponDAO=new WeaponDAO();
		ScepterDAO scepterDAO=new ScepterDAO();
		BookDAO bookDAO=new BookDAO();
		Weapon weapon=weaponDAO.findById(2);
		Scepter scepter=scepterDAO.findById(2);
		Book book=bookDAO.findById(2);
		sayre.setWeapon(weapon);
		sayre.setScepter(scepter);
		sayre.setBook(book);
		sayre.setCoins(100);
		return sayre;
	}
	public static Sayre setUnarmed(Sayre sayre) {
		WeaponDAO weaponDAO=new WeaponDAO();
		ScepterDAO scepterDAO=new ScepterDAO();
		BookDAO bookDAO=new BookDAO();
		Weapon weapon=weaponDAO.findById(1);
		Scepter scepter=scepterDAO.findById(1);
		Book book=bookDAO.findById(1);
		sayre.setWeapon(weapon);
		sayre.setScepter(scepter);
		sayre.setBook(book);
		return sayre;
	}
	
}
