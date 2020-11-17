/*
Interface lab assignment. 
Study the below code and then make the changes outlined in the below comments
11-12-2020
*/
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.MidiChannel;
import java.util.Scanner;
 

 //This interface is used to select notes from an array and print out instrument choices. 
interface midiPlayer
{
	//These are just the notes you can choose from. 
	public static String [] notes = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

	//This prints out the available instruments to choose from. 
	public static void printInstruments()
		{
			System.out.println("Available Instruments are:");
			System.out.println("PIANO");
			System.out.println("HARPSICHORD");
			System.out.println("XYLOPHONE");
			System.out.println("CHURCH_ORGAN");
			System.out.println("REED_ORGAN");
			System.out.println("HARMONICA");
			System.out.println("GUITAR");
			System.out.println("ELECTRIC_GUITAR");
			System.out.println("VIOLIN");
			System.out.println("HARP");
			System.out.println("TIMPANI");
			System.out.println("TRUMPET");
			System.out.println("TROMBONE");
			System.out.println("OBOE");
			System.out.println("FLUTE");
			System.out.println("BANJO");
			System.out.println("STEEL_DRUMS");
		}
}


//This interface is used to select which instrument you want to use. 
interface midiInstrument 
{
	//The midiValues for each instrument
	final int 	PIANO = 0,
				HARPSICHORD = 6,
				XYLOPHONE = 13,
				CHURCH_ORGAN = 19,
				REED_ORGAN = 20,
				HARMONICA = 22,
				GUITAR = 24,
				ELECTRIC_GUITAR = 27,
				VIOLIN = 40,
				HARP = 46,
				TIMPANI = 47,
				TRUMPET = 56,
				TROMBONE = 57,
				OBOE = 68,
				FLUTE = 73,
				BANJO = 105,
				STEEL_DRUMS = 114;
				
	//This method is used to select an instrument based on the midiValues above
	public static int getMidiValue(String instrument)
	{
		if (instrument.equals("PIANO")) 			return 	PIANO;
		if (instrument.equals("HARPSICHORD")) 		return	HARPSICHORD;
		if (instrument.equals("XYLOPHONE")) 		return 	XYLOPHONE;
		if (instrument.equals("CHURCH_ORGAN")) 		return 	CHURCH_ORGAN;
		if (instrument.equals("REED_ORGAN")) 		return 	REED_ORGAN;
		if (instrument.equals("HARMONICA")) 		return 	HARMONICA;
		if (instrument.equals("GUITAR")) 			return 	GUITAR;
		if (instrument.equals("ELECTRIC_GUITAR")) 	return 	ELECTRIC_GUITAR;
		if (instrument.equals("VIOLIN")) 			return 	VIOLIN;
		if (instrument.equals("HARP")) 				return 	HARP;
		if (instrument.equals("TIMPANI")) 			return 	TIMPANI;
		if (instrument.equals("TRUMPET")) 			return 	TRUMPET;
		if (instrument.equals("TROMBONE")) 			return 	TROMBONE;
		if (instrument.equals("OBOE")) 				return 	OBOE;
		if (instrument.equals("FLUTE")) 			return 	FLUTE;
		if (instrument.equals("BANJO")) 			return 	BANJO;
		if (instrument.equals("STEEL_DRUMS")) 		return 	STEEL_DRUMS;
		
		else return 0;
	}
		
}

// This class is in charge of creating an instrument based on the two interfaces above.
 class Instrument implements midiPlayer, midiInstrument
	{
		private String name; //instrument name. Has to match one of the options listed in the above interfaces
		private int midiValue; //numeric value used to tell the synth what instrument to mimic
		private MidiChannel[] channels; //all the available midi channels
		
		//SET TO PIANO BY DEFAULT
		public Instrument(MidiChannel[] channels)
		{
			this.channels = channels;
			midiValue = 0;
		}
		
		//SET THE MIDIVALUE USING THE SENT NAME
		//THE METHOD FOR THIS HAS ALREADY BEEN CREATED
		public Instrument(MidiChannel[] channels, String name)
		{
			this.name=name; 
			this.channels=channels;
			midiValue = midiInstrument.getMidiValue(name);
		}
		
		public int getMidiValue(){return midiValue;}
		
	//This method plays the sent note given a duration	
	public void play(String note, int duration) throws InterruptedException
	{
			int VOLUME = 80;
			
			//changes the instrument based on the midiValue
			channels[0].programChange(midiValue);
		
			// * start playing a note
			channels[0].noteOn(id(note), VOLUME );
			// * wait
			Thread.sleep( duration );
			// * stop playing a note
			channels[0].noteOff(id(note));
	}
	
	//Given the string name of a note calculate the int value needed to play that note
	//ADD COMMENTS THROUGHOUT EXPLAINING WHAT IS HAPPENING
	private static int id(String note)
	{
		//finds the ovtave the note is in to send to the calculated return value. 
		int octave = Integer.parseInt(note.substring(0, 1));
		String noteE = note.substring(1);
		
		//finds the index in the notes array to send to the calculated retun value. 
		int index;
		for(index = 0; index< notes.length; index++) if (noteE.equals(notes[index])) break;
		
		//sends a calculated value to the channel in order to know what note is supposed to play. 
		return index + 12 * octave + 12;	
	}
	
	//puts application to sleep to mimic a rest
	public void rest(int duration) throws InterruptedException
	{
		Thread.sleep(duration);
	}
	
}





public class InLab implements midiPlayer
{
	//This method is how we play a song.
	public static void main( String[] args ) {
		
		try {
			Scanner scn = new Scanner(System.in);
				
			// * Open a synthesizer
			Synthesizer synth = MidiSystem.getSynthesizer();
			MidiChannel[] channels;
			synth.open();
			channels = synth.getChannels();
			
			// * Prints instruments to choose from, and allows User to choose one. 
			midiPlayer.printInstruments();
			System.out.println("Select an Instrument in all caps");
			
			// * Creates an instrument based on the User's choice of instrument type. 
			Instrument ins = new Instrument(channels, scn.next());
			
			//System.out.println("Playing the first song..."); song1(ins);
			
			//System.out.println("Playing the second song..."); song2(ins);
			
			System.out.println("Bonus song for fun..."); songCUSTOM(ins);
			
			// * finish up
			synth.close();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	//This is the song from the original demo
	public static void song1(Instrument ins)
	{
		
		try {
			ins.play("6D",  1000);
			ins.rest(500);
			ins.play("6D",  300);
			ins.play("6C#", 300);
			ins.play("6D",  1000);
			ins.rest(500);
			ins.play("6D",  300);
			ins.play("6C#", 300);
			ins.play("6D",  1000);
			ins.play("6E",  300);
			ins.play("6E",  600);
			ins.play("6G",  300);
			ins.play("6G",  600);
			ins.rest(500);	
			}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	//This is the song I want you to play now
	public static void song2(Instrument ins)
	{
		try {
			ins.play("6C",  1500);
			ins.play("6C",  1500);
			ins.play("6C",  1000);
			ins.play("6D",  500);
			ins.play("6E",  1500);
			ins.play("6E",  1000);
			ins.play("6D",  500);
			ins.play("6E", 1000);
			ins.play("6F",  500);
			ins.play("6G",  3000);
			ins.play("7C",  500);
			ins.play("7C",  500);
			ins.play("7C",  500);
			ins.play("6G",  500);
			ins.play("6G",  500);
			ins.play("6G",  500);
			ins.play("6E", 500);
			ins.play("6E", 500);
			ins.play("6E", 500);
			//6th G for 1 second
			ins.play("6G", 1000);
			//6th F for 1/2 a second
			ins.play("6F", 500);
			//6th E for 1 second
			ins.play("6E", 1000);
			//6th D for 1/2 a second
			ins.play("6D", 500);
			//6th C for 3 seconds
			ins.play("6C", 3000);
			}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	// * This is a custom song I painstakingly made just for fun. 
	public static void songCUSTOM(Instrument ins)
	{
		//Giorno's Theme
		try {
			//Intro part
			ins.play("4F#", 500);
			ins.play("4D", 600);
			ins.rest(100);
			ins.play("4D", 150);
			ins.play("4E", 150);
			ins.play("4F", 300);
			ins.play("4E", 300);
			ins.play("4D", 250);
			ins.play("4C#", 350);
			ins.play("4D", 300);
			ins.play("4E", 200);
			ins.play("4F#", 500);
			ins.rest(100);
			ins.play("4B", 600);
			ins.rest(100);
			ins.play("3B", 200);
			ins.play("4C#", 200);
			ins.play("4D", 350);
			ins.play("4E", 350);
			ins.play("4D", 200);
			ins.play("4C#", 350);
			ins.play("4A", 300);
			ins.play("4G", 200);
			ins.play("4F#", 600);
			ins.rest(100);
			ins.play("4D", 600);
			ins.rest(100);
			ins.play("4D", 150);
			ins.play("4E", 150);
			ins.play("4F", 300);
			ins.play("4E", 300);
			ins.play("4D", 250);
			ins.play("4C#", 350);
			ins.play("4D", 300);
			ins.play("4E", 200);
			ins.play("4F#", 500);
			ins.rest(100);
			ins.play("4B", 600);
			ins.rest(100);
			ins.play("4B", 150);
			ins.play("5C#", 200);
			ins.play("5D", 300);
			ins.play("4G", 300);
			ins.play("4F#", 250);
			ins.play("4F", 300);
			ins.play("5D", 300);
			ins.play("4A#", 200);
			//Switches to next part
			ins.play("3B", 200);
			ins.play("3B", 200);
			ins.play("3B", 200);
			ins.play("3A", 100);
			ins.play("3B", 200);
			ins.play("4D", 200);
			ins.play("3B", 200);
			ins.play("3F#", 150);
			ins.play("3A", 150);
			ins.play("3B", 200);
			ins.play("3B", 200);
			ins.play("3B", 200);
			ins.play("3A", 150);
			ins.play("3B", 200);
			ins.play("4F", 200);
			ins.play("4E", 200);
			ins.play("4D", 150);
			ins.play("3A", 250);
			ins.play("3B", 200);
			ins.play("3B", 200);
			ins.play("3B", 200);
			ins.play("3A", 100);
			ins.play("3B", 200);
			ins.play("4D", 200);
			ins.play("3B", 200);
			ins.play("3F#", 150);
			ins.play("3A", 150);
			ins.play("3B", 200);
			ins.play("3B", 200);
			ins.play("3B", 200);
			ins.play("3A", 150);
			ins.play("3B", 200);
			ins.play("4F", 200);
			ins.play("4E", 200);
			ins.play("4D", 150);
			ins.play("3A", 250);
			//Goes up an octave and plays same part
			ins.play("4B", 200);
			ins.play("4B", 200);
			ins.play("4B", 200);
			ins.play("4A", 100);
			ins.play("4B", 200);
			ins.play("5D", 200);
			ins.play("4B", 200);
			ins.play("4F#", 150);
			ins.play("4A", 150);
			ins.play("4B", 200);
			ins.play("4B", 200);
			ins.play("4B", 200);
			ins.play("4A", 150);
			ins.play("4B", 200);
			ins.play("5F", 200);
			ins.play("5E", 200);
			ins.play("5D", 150);
			ins.play("4A", 250);
			ins.play("4B", 200);
			ins.play("4B", 200);
			ins.play("4B", 200);
			ins.play("4A", 100);
			ins.play("4B", 200);
			ins.play("5D", 200);
			ins.play("4B", 200);
			ins.play("4F#", 150);
			ins.play("4A", 150);
			ins.play("4B", 200);
			ins.play("4B", 200);
			ins.play("4B", 200);
			ins.play("4A", 125);
			ins.play("4B", 200);
			//Added a rest to make sure the last note fully plays
			//Sometimes without the rest, it just skipped the last note
			ins.rest(500);
			}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
		
}