// Copyright (c) 2003-present, Jodd Team (http://jodd.org)
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
// 1. Redistributions of source code must retain the above copyright notice,
// this list of conditions and the following disclaimer.
//
// 2. Redistributions in binary form must reproduce the above copyright
// notice, this list of conditions and the following disclaimer in the
// documentation and/or other materials provided with the distribution.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
// LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
// SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
// CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
// POSSIBILITY OF SUCH DAMAGE.

package jodd.madvoc;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.json.JsonParser;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public abstract class TagActionTestBase {

	@Test
	public void testDisableTag() {
		HttpResponse response = HttpRequest
				.get("localhost:8173/tag/disable/123")
				.send();
		assertEquals("disable-Tag{123:jodd}", response.bodyText().trim());
	}

	@Test
	public void testDeleteTag() {
		HttpResponse response = HttpRequest
				.get("localhost:8173/tag/delete/123")
				.send();
		assertEquals("delete-Tag{123:jodd}", response.bodyText().trim());
	}

	@Test
	public void testEditTag() {
		HttpResponse response = HttpRequest
				.get("localhost:8173/tag/edit/123")
				.query("tag.name", "ddoj")
				.send();
		assertEquals("edit-Tag{123:ddoj}", response.bodyText().trim());
	}

	@Test
	public void testSaveTag() {
		HttpResponse response = HttpRequest
				.get("localhost:8173/tag/save/123")
				.query("tag.name", "JODD")
				.send();
		assertEquals("save-Tag{123:JODD}", response.bodyText().trim());
	}

	@Test
	void testError() {
		HttpResponse response = HttpRequest
			.get("localhost:8173/tag/boom")
			.send();

		assertEquals(500, response.statusCode());
		String body = response.bodyText();
		Map map = JsonParser.createJsonParser().parse(body);

		assertEquals("/ by zero", map.get("message"));
		assertEquals("java.lang.ArithmeticException", map.get("error"));

		List<String> details = (List<String>) map.get("details");
		assertFalse(details.isEmpty());
	}


}
