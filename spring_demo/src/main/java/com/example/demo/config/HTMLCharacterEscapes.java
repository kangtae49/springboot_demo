package com.example.demo.config;





import org.apache.commons.text.StringEscapeUtils;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;

@SuppressWarnings("serial")
public class HTMLCharacterEscapes extends CharacterEscapes {
	private final int[] asciiEscapes;

	public HTMLCharacterEscapes() {
		// XSS 방지 처리할 특수 문자 지정
		asciiEscapes = CharacterEscapes.standardAsciiEscapesForJSON();
		asciiEscapes['<'] = CharacterEscapes.ESCAPE_CUSTOM;
		asciiEscapes['>'] = CharacterEscapes.ESCAPE_CUSTOM;
		asciiEscapes['&'] = CharacterEscapes.ESCAPE_CUSTOM;
	}

	@Override
	public int[] getEscapeCodesForAscii() {
		return asciiEscapes;
	}

	@Override
	public SerializableString getEscapeSequence(int ch) {
		
		// return new SerializedString(XssPreventer.escape(Character.toString((char) ch)));
		
		return new SerializedString(StringEscapeUtils.escapeHtml4(Character.toString((char) ch)));
	}

}
