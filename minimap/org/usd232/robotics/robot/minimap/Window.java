package org.usd232.robotics.robot.minimap;

import java.nio.IntBuffer;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class Window implements Runnable {
    private long id;
    private static final float[] RED = {1, 0, 0,  0.25f};
    private static final float[] LIGHT_RED = {1, 0, 0,  1f / 12f};
    private static final float[] BLUE = {0, 0, 1,  0.25f};
    private static final float[] LIGHT_BLUE = {0, 0, 1,  1f / 12f};
    private static final float[] BLACK = {0, 0, 0};
    private static final float[] GREEN = {0, 1, 0};
    private static final float[] GREY = {0, 0, 0, 0.125f};
    private static final float FIELD_WIDTH = 27;
    private static final float FIELD_HEIGHT = 54;
    private static final float AUTO_LINE = 10; 
    private static final float NULL_WIDTH = 7f + (11.25f / 12f);
    private static final float NULL_HEIGHT = 6;
    private static final float[] TEAM_COLOR = (1 % 2 == 1) ? RED : BLUE;
    private static final float[] TEAM_LIGHT_COLOR = (1 % 2 == 1) ? LIGHT_RED : LIGHT_BLUE;
    private static final float[] OPP_COLOR = (1 % 2 == 0) ? RED : BLUE;
    private static final float[] OPP_LIGHT_COLOR = (1 % 2 == 0) ? LIGHT_RED : LIGHT_BLUE;
    private static final float EXCHANGE_X = 8.5f;
    private static final float EXCHANGE_WIDTH = 4;
    private static final float EXCHANGE_HEIGHT = 3;
    private static final float SWITCH_CENTER_Y = 14;
    private static final float SWITCH_WIDTH = 12f + (9.5f / 12f);
    private static final float SWITCH_HEIGHT = 4f + (8f / 12f);
    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    private static final int NEAR_SWITCH_STATE = LEFT; //left side of the switch is that of our team
    private static final int SCALE_STATE = RIGHT; //right side of the switch is that of our team
    private static final int FAR_SWITCH_STATE = RIGHT; //right side of the switch is that of our team
    private static final float PLATE_WIDTH = 3;
    private static final float PLATE_HEIGHT = 4;
    private static final float SWITCH_PLATE_CENTER_X = 4.5f;
    private static final float SCALE_PLATE_CENTER_X = 6;
    private static final float SCALE_ARM_THICKNESS = 1f + (2f / 12f);
    private static final float SCALE_ARM_WIDTH = 9;
    private static final float PLATFORM_WIDTH = 8f + (8f / 12f);
    private static final float PLATFORM_HEIGHT = 3f + (5.25f / 12f);
    private static final float PLATFORM_Y_OFFSET = SCALE_ARM_THICKNESS / 2f;
    private static final float RAMP_WIDTH = PLATFORM_WIDTH + 2f * (1f + (1f / 12f));
    private static final float RAMP_HEIGHT = PLATFORM_HEIGHT + (1f + (1f / 12f));
    private static final float PLATFORM_ZONE_WIDTH = RAMP_WIDTH;
    private static final float PLATFORM_ZONE_HEIGHT = 10;
    private static final float POWER_CUBE_ZONE_WIDTH = 3.75f;
    private static final float POWER_CUBE_ZONE_HEIGHT = 3.5f;
    private static final float POWER_CUBE_ZONE_CENTER_Y = SWITCH_CENTER_Y - (SWITCH_HEIGHT + POWER_CUBE_ZONE_HEIGHT) / 2f;
    private static final float PORTAL_WIDTH = 26.69f / 12f;
    private static final float PORTAL_HEIGHT = 35f / 12f;
    private static MinimapCoordsClient client;
    private static final float ROBOT_LENGTH = 24.5f / 12f;
    private static final float ROBOT_WIDTH = 22.5f / 12f;
    private static final float ROBOT_COM = 6f / 12f;
    private static double robotX = 10;
    private static double robotY = 5;
    private static double robotAngle = 0;
    private static int windowHeight = 800;
    
    private void render() {
    	GL11.glLoadIdentity();
    	GL11.glTranslated(-1, -1, 0);
    	GL11.glScaled(2. / FIELD_WIDTH, 2. / FIELD_HEIGHT, 1);

    	robotX = client.getX();
    	robotY = client.getY();
    	robotAngle = client.getAngle();
    	
    	doLines();
    	doNullTerritories();
    	doExchanges();
    	doSwitches();
    	doScale();
    	doPlatformZone();
    	doPowerCubeZone();
    	doRobot();
    	doBoundary();
    }
    
    private void doLines() {    	
    	GL11.glColor3fv(BLACK);
    	
    	GL11.glBegin(GL11.GL_LINES);
    	GL11.glVertex2f(0, AUTO_LINE);
    	GL11.glVertex2f(FIELD_WIDTH, AUTO_LINE);
    	GL11.glEnd();
    	
    	GL11.glBegin(GL11.GL_LINES);
    	GL11.glVertex2f(0, FIELD_HEIGHT - AUTO_LINE);
    	GL11.glVertex2f(FIELD_WIDTH, FIELD_HEIGHT - AUTO_LINE);
    	GL11.glEnd();
    	
    	GL11.glBegin(GL11.GL_LINES);
    	GL11.glVertex2f(0, FIELD_HEIGHT / 2f);
    	GL11.glVertex2f(FIELD_WIDTH, FIELD_HEIGHT / 2f);
    	GL11.glEnd();
    }
    
    private void doNullTerritories() {
    	GL11.glColor4fv(GREY);
    	
    	GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(0, (FIELD_HEIGHT + NULL_HEIGHT) / 2);
        GL11.glVertex2f(NULL_WIDTH, (FIELD_HEIGHT + NULL_HEIGHT) / 2);
        GL11.glVertex2f(NULL_WIDTH, (FIELD_HEIGHT - NULL_HEIGHT) / 2);
        GL11.glVertex2f(0, (FIELD_HEIGHT - NULL_HEIGHT) / 2);
        GL11.glEnd();

    	GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(FIELD_WIDTH, (FIELD_HEIGHT + NULL_HEIGHT) / 2);
        GL11.glVertex2f(FIELD_WIDTH - NULL_WIDTH, (FIELD_HEIGHT + NULL_HEIGHT) / 2);
        GL11.glVertex2f(FIELD_WIDTH - NULL_WIDTH, (FIELD_HEIGHT - NULL_HEIGHT) / 2);
        GL11.glVertex2f(FIELD_WIDTH, (FIELD_HEIGHT - NULL_HEIGHT) / 2);
        GL11.glEnd();
        

    	GL11.glColor3fv(BLACK);
    	
    	GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2f(0, (FIELD_HEIGHT + NULL_HEIGHT) / 2);
        GL11.glVertex2f(NULL_WIDTH, (FIELD_HEIGHT + NULL_HEIGHT) / 2);
        GL11.glVertex2f(NULL_WIDTH, (FIELD_HEIGHT - NULL_HEIGHT) / 2);
        GL11.glVertex2f(0, (FIELD_HEIGHT - NULL_HEIGHT) / 2);
        GL11.glEnd();

    	GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2f(FIELD_WIDTH, (FIELD_HEIGHT + NULL_HEIGHT) / 2);
        GL11.glVertex2f(FIELD_WIDTH - NULL_WIDTH, (FIELD_HEIGHT + NULL_HEIGHT) / 2);
        GL11.glVertex2f(FIELD_WIDTH - NULL_WIDTH, (FIELD_HEIGHT - NULL_HEIGHT) / 2);
        GL11.glVertex2f(FIELD_WIDTH, (FIELD_HEIGHT - NULL_HEIGHT) / 2);
        GL11.glEnd();
    }
    
    private void doExchanges() {
    	GL11.glColor4fv(TEAM_COLOR);
    	GL11.glBegin(GL11.GL_QUADS);
    	GL11.glVertex2f(EXCHANGE_X, EXCHANGE_HEIGHT);
    	GL11.glVertex2f(EXCHANGE_X + EXCHANGE_WIDTH, EXCHANGE_HEIGHT);
    	GL11.glVertex2f(EXCHANGE_X + EXCHANGE_WIDTH, 0);
    	GL11.glVertex2f(EXCHANGE_X, 0);
        GL11.glEnd();

    	GL11.glColor4fv(OPP_COLOR);
    	GL11.glBegin(GL11.GL_QUADS);
    	GL11.glVertex2f(FIELD_WIDTH - EXCHANGE_X, FIELD_HEIGHT - EXCHANGE_HEIGHT);
    	GL11.glVertex2f(FIELD_WIDTH - (EXCHANGE_X + EXCHANGE_WIDTH), FIELD_HEIGHT - EXCHANGE_HEIGHT);
    	GL11.glVertex2f(FIELD_WIDTH - (EXCHANGE_X + EXCHANGE_WIDTH), FIELD_HEIGHT);
    	GL11.glVertex2f(FIELD_WIDTH - EXCHANGE_X, FIELD_HEIGHT);
        GL11.glEnd();
        

    	GL11.glColor3fv(BLACK);
    	
    	GL11.glBegin(GL11.GL_LINE_LOOP);
    	GL11.glVertex2f(EXCHANGE_X, EXCHANGE_HEIGHT);
    	GL11.glVertex2f(EXCHANGE_X + EXCHANGE_WIDTH, EXCHANGE_HEIGHT);
    	GL11.glVertex2f(EXCHANGE_X + EXCHANGE_WIDTH, 0);
    	GL11.glVertex2f(EXCHANGE_X, 0);
        GL11.glEnd();

    	GL11.glBegin(GL11.GL_LINE_LOOP);
    	GL11.glVertex2f(FIELD_WIDTH - EXCHANGE_X, FIELD_HEIGHT - EXCHANGE_HEIGHT);
    	GL11.glVertex2f(FIELD_WIDTH - (EXCHANGE_X + EXCHANGE_WIDTH), FIELD_HEIGHT - EXCHANGE_HEIGHT);
    	GL11.glVertex2f(FIELD_WIDTH - (EXCHANGE_X + EXCHANGE_WIDTH), FIELD_HEIGHT);
    	GL11.glVertex2f(FIELD_WIDTH - EXCHANGE_X, FIELD_HEIGHT);
        GL11.glEnd();
    }
    
    private void doSwitches() {
    	GL11.glColor3fv(BLACK);
    	GL11.glBegin(GL11.GL_LINE_LOOP);
    	GL11.glVertex2f((FIELD_WIDTH + SWITCH_WIDTH) / 2, SWITCH_CENTER_Y + SWITCH_HEIGHT / 2);
    	GL11.glVertex2f((FIELD_WIDTH + SWITCH_WIDTH) / 2, SWITCH_CENTER_Y - SWITCH_HEIGHT / 2);
    	GL11.glVertex2f((FIELD_WIDTH - SWITCH_WIDTH) / 2, SWITCH_CENTER_Y - SWITCH_HEIGHT / 2);
    	GL11.glVertex2f((FIELD_WIDTH - SWITCH_WIDTH) / 2, SWITCH_CENTER_Y + SWITCH_HEIGHT / 2);
        GL11.glEnd();

    	GL11.glBegin(GL11.GL_LINE_LOOP);
    	GL11.glVertex2f((FIELD_WIDTH + SWITCH_WIDTH) / 2, FIELD_HEIGHT - SWITCH_CENTER_Y + SWITCH_HEIGHT / 2);
    	GL11.glVertex2f((FIELD_WIDTH + SWITCH_WIDTH) / 2, FIELD_HEIGHT - SWITCH_CENTER_Y - SWITCH_HEIGHT / 2);
    	GL11.glVertex2f((FIELD_WIDTH - SWITCH_WIDTH) / 2, FIELD_HEIGHT - SWITCH_CENTER_Y - SWITCH_HEIGHT / 2);
    	GL11.glVertex2f((FIELD_WIDTH - SWITCH_WIDTH) / 2, FIELD_HEIGHT - SWITCH_CENTER_Y + SWITCH_HEIGHT / 2);
        GL11.glEnd();

    	GL11.glColor4fv(TEAM_COLOR);
    	
    	GL11.glPushMatrix();
    	GL11.glTranslatef(FIELD_WIDTH / 2f - SWITCH_PLATE_CENTER_X + (2f * NEAR_SWITCH_STATE * SWITCH_PLATE_CENTER_X), 14f, 0f);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(-PLATE_WIDTH / 2, -PLATE_HEIGHT / 2);
        GL11.glVertex2f(-PLATE_WIDTH / 2, PLATE_HEIGHT / 2);
        GL11.glVertex2f(PLATE_WIDTH / 2, PLATE_HEIGHT / 2);
        GL11.glVertex2f(PLATE_WIDTH / 2, -PLATE_HEIGHT / 2);
        GL11.glEnd();
        GL11.glPopMatrix();

    	GL11.glColor4fv(OPP_COLOR);
    	
    	GL11.glPushMatrix();
    	GL11.glTranslatef(FIELD_WIDTH / 2f + SWITCH_PLATE_CENTER_X - (2f * NEAR_SWITCH_STATE * SWITCH_PLATE_CENTER_X), 14f, 0f);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(-PLATE_WIDTH / 2, -PLATE_HEIGHT / 2);
        GL11.glVertex2f(-PLATE_WIDTH / 2, PLATE_HEIGHT / 2);
        GL11.glVertex2f(PLATE_WIDTH / 2, PLATE_HEIGHT / 2);
        GL11.glVertex2f(PLATE_WIDTH / 2, -PLATE_HEIGHT / 2);
        GL11.glEnd();
        GL11.glPopMatrix();

    	GL11.glColor4fv(TEAM_COLOR);
    	
    	GL11.glPushMatrix();
    	GL11.glTranslatef(FIELD_WIDTH / 2f - SWITCH_PLATE_CENTER_X + (2f * FAR_SWITCH_STATE * SWITCH_PLATE_CENTER_X), FIELD_HEIGHT - 14f, 0f);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(-PLATE_WIDTH / 2, -PLATE_HEIGHT / 2);
        GL11.glVertex2f(-PLATE_WIDTH / 2, PLATE_HEIGHT / 2);
        GL11.glVertex2f(PLATE_WIDTH / 2, PLATE_HEIGHT / 2);
        GL11.glVertex2f(PLATE_WIDTH / 2, -PLATE_HEIGHT / 2);
        GL11.glEnd();
        GL11.glPopMatrix();

    	GL11.glColor4fv(OPP_COLOR);
    	
    	GL11.glPushMatrix();
    	GL11.glTranslatef(FIELD_WIDTH / 2f + SWITCH_PLATE_CENTER_X - (2f * FAR_SWITCH_STATE * SWITCH_PLATE_CENTER_X), FIELD_HEIGHT -  14f, 0f);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(-PLATE_WIDTH / 2, -PLATE_HEIGHT / 2);
        GL11.glVertex2f(-PLATE_WIDTH / 2, PLATE_HEIGHT / 2);
        GL11.glVertex2f(PLATE_WIDTH / 2, PLATE_HEIGHT / 2);
        GL11.glVertex2f(PLATE_WIDTH / 2, -PLATE_HEIGHT / 2);
        GL11.glEnd();
        GL11.glPopMatrix();

        
    	GL11.glColor3fv(BLACK);
    	
    	GL11.glPushMatrix();
    	GL11.glTranslatef(FIELD_WIDTH / 2f - SWITCH_PLATE_CENTER_X + (2f * NEAR_SWITCH_STATE * SWITCH_PLATE_CENTER_X), 14f, 0f);
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2f(-PLATE_WIDTH / 2, -PLATE_HEIGHT / 2);
        GL11.glVertex2f(-PLATE_WIDTH / 2, PLATE_HEIGHT / 2);
        GL11.glVertex2f(PLATE_WIDTH / 2, PLATE_HEIGHT / 2);
        GL11.glVertex2f(PLATE_WIDTH / 2, -PLATE_HEIGHT / 2);
        GL11.glEnd();
        GL11.glPopMatrix();
    	
    	GL11.glPushMatrix();
    	GL11.glTranslatef(FIELD_WIDTH / 2f + SWITCH_PLATE_CENTER_X - (2f * NEAR_SWITCH_STATE * SWITCH_PLATE_CENTER_X), 14f, 0f);
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2f(-PLATE_WIDTH / 2, -PLATE_HEIGHT / 2);
        GL11.glVertex2f(-PLATE_WIDTH / 2, PLATE_HEIGHT / 2);
        GL11.glVertex2f(PLATE_WIDTH / 2, PLATE_HEIGHT / 2);
        GL11.glVertex2f(PLATE_WIDTH / 2, -PLATE_HEIGHT / 2);
        GL11.glEnd();
        GL11.glPopMatrix();
    	
    	GL11.glPushMatrix();
    	GL11.glTranslatef(FIELD_WIDTH / 2f - SWITCH_PLATE_CENTER_X + (2f * FAR_SWITCH_STATE * SWITCH_PLATE_CENTER_X), FIELD_HEIGHT - 14f, 0f);
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2f(-PLATE_WIDTH / 2, -PLATE_HEIGHT / 2);
        GL11.glVertex2f(-PLATE_WIDTH / 2, PLATE_HEIGHT / 2);
        GL11.glVertex2f(PLATE_WIDTH / 2, PLATE_HEIGHT / 2);
        GL11.glVertex2f(PLATE_WIDTH / 2, -PLATE_HEIGHT / 2);
        GL11.glEnd();
        GL11.glPopMatrix();
    	
    	GL11.glPushMatrix();
    	GL11.glTranslatef(FIELD_WIDTH / 2f + SWITCH_PLATE_CENTER_X - (2f * FAR_SWITCH_STATE * SWITCH_PLATE_CENTER_X), FIELD_HEIGHT -  14f, 0f);
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2f(-PLATE_WIDTH / 2, -PLATE_HEIGHT / 2);
        GL11.glVertex2f(-PLATE_WIDTH / 2, PLATE_HEIGHT / 2);
        GL11.glVertex2f(PLATE_WIDTH / 2, PLATE_HEIGHT / 2);
        GL11.glVertex2f(PLATE_WIDTH / 2, -PLATE_HEIGHT / 2);
        GL11.glEnd();
        GL11.glPopMatrix();
    }
    
    private void doScale() {
    	GL11.glColor3fv(BLACK);
    	
    	GL11.glPushMatrix();
    	GL11.glTranslatef(FIELD_WIDTH / 2f, FIELD_HEIGHT / 2f, 0f);
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2f(-SCALE_ARM_WIDTH / 2, -SCALE_ARM_THICKNESS / 2);
        GL11.glVertex2f(-SCALE_ARM_WIDTH / 2, SCALE_ARM_THICKNESS / 2);
        GL11.glVertex2f(SCALE_ARM_WIDTH / 2, SCALE_ARM_THICKNESS / 2);
        GL11.glVertex2f(SCALE_ARM_WIDTH / 2, -SCALE_ARM_THICKNESS / 2);
        GL11.glEnd();
        GL11.glPopMatrix();

    	GL11.glColor4fv(TEAM_COLOR);
    	
    	GL11.glPushMatrix();
    	GL11.glTranslatef(FIELD_WIDTH / 2f - SCALE_PLATE_CENTER_X + (2f * SCALE_STATE * SCALE_PLATE_CENTER_X), FIELD_HEIGHT / 2f, 0f);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(-PLATE_WIDTH / 2, -PLATE_HEIGHT / 2);
        GL11.glVertex2f(-PLATE_WIDTH / 2, PLATE_HEIGHT / 2);
        GL11.glVertex2f(PLATE_WIDTH / 2, PLATE_HEIGHT / 2);
        GL11.glVertex2f(PLATE_WIDTH / 2, -PLATE_HEIGHT / 2);
        GL11.glEnd();
        GL11.glPopMatrix();

    	GL11.glColor4fv(OPP_COLOR);
    	
    	GL11.glPushMatrix();
    	GL11.glTranslatef(FIELD_WIDTH / 2f + SCALE_PLATE_CENTER_X - (2f * SCALE_STATE * SCALE_PLATE_CENTER_X), FIELD_HEIGHT / 2f, 0f);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(-PLATE_WIDTH / 2, -PLATE_HEIGHT / 2);
        GL11.glVertex2f(-PLATE_WIDTH / 2, PLATE_HEIGHT / 2);
        GL11.glVertex2f(PLATE_WIDTH / 2, PLATE_HEIGHT / 2);
        GL11.glVertex2f(PLATE_WIDTH / 2, -PLATE_HEIGHT / 2);
        GL11.glEnd();
        GL11.glPopMatrix();

    	GL11.glColor3fv(BLACK);
    	
    	GL11.glPushMatrix();
    	GL11.glTranslatef(FIELD_WIDTH / 2f - SCALE_PLATE_CENTER_X + (2f * SCALE_STATE * SCALE_PLATE_CENTER_X), FIELD_HEIGHT / 2f, 0f);
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2f(-PLATE_WIDTH / 2, -PLATE_HEIGHT / 2);
        GL11.glVertex2f(-PLATE_WIDTH / 2, PLATE_HEIGHT / 2);
        GL11.glVertex2f(PLATE_WIDTH / 2, PLATE_HEIGHT / 2);
        GL11.glVertex2f(PLATE_WIDTH / 2, -PLATE_HEIGHT / 2);
        GL11.glEnd();
        GL11.glPopMatrix();
    	
    	GL11.glPushMatrix();
    	GL11.glTranslatef(FIELD_WIDTH / 2f + SCALE_PLATE_CENTER_X - (2f * SCALE_STATE * SCALE_PLATE_CENTER_X), FIELD_HEIGHT / 2f, 0f);
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2f(-PLATE_WIDTH / 2, -PLATE_HEIGHT / 2);
        GL11.glVertex2f(-PLATE_WIDTH / 2, PLATE_HEIGHT / 2);
        GL11.glVertex2f(PLATE_WIDTH / 2, PLATE_HEIGHT / 2);
        GL11.glVertex2f(PLATE_WIDTH / 2, -PLATE_HEIGHT / 2);
        GL11.glEnd();
        GL11.glPopMatrix();
    }

    private void doPlatformZone() {
    	GL11.glPushMatrix();
    	GL11.glTranslatef(FIELD_WIDTH / 2, FIELD_HEIGHT / 2, 0);

    	GL11.glColor4fv(TEAM_COLOR);

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(-PLATFORM_WIDTH / 2, -PLATFORM_Y_OFFSET);
        GL11.glVertex2f(-PLATFORM_WIDTH / 2, -(PLATFORM_HEIGHT + PLATFORM_Y_OFFSET));
        GL11.glVertex2f(PLATFORM_WIDTH / 2, -(PLATFORM_HEIGHT + PLATFORM_Y_OFFSET));
        GL11.glVertex2f(PLATFORM_WIDTH / 2, -PLATFORM_Y_OFFSET);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(-RAMP_WIDTH / 2, -PLATFORM_Y_OFFSET);
        GL11.glVertex2f(-RAMP_WIDTH / 2, -(RAMP_HEIGHT + PLATFORM_Y_OFFSET));
        GL11.glVertex2f(RAMP_WIDTH / 2, -(RAMP_HEIGHT + PLATFORM_Y_OFFSET));
        GL11.glVertex2f(RAMP_WIDTH / 2, -PLATFORM_Y_OFFSET);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(-PLATFORM_ZONE_WIDTH / 2, -PLATFORM_Y_OFFSET);
        GL11.glVertex2f(-PLATFORM_ZONE_WIDTH / 2, -(PLATFORM_ZONE_HEIGHT + PLATFORM_Y_OFFSET));
        GL11.glVertex2f(PLATFORM_ZONE_WIDTH / 2, -(PLATFORM_ZONE_HEIGHT + PLATFORM_Y_OFFSET));
        GL11.glVertex2f(PLATFORM_ZONE_WIDTH / 2, -PLATFORM_Y_OFFSET);
        GL11.glEnd();

        
    	GL11.glColor3fv(BLACK);

        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2f(-PLATFORM_WIDTH / 2, -PLATFORM_Y_OFFSET);
        GL11.glVertex2f(-PLATFORM_WIDTH / 2, -(PLATFORM_HEIGHT + PLATFORM_Y_OFFSET));
        GL11.glVertex2f(PLATFORM_WIDTH / 2, -(PLATFORM_HEIGHT + PLATFORM_Y_OFFSET));
        GL11.glVertex2f(PLATFORM_WIDTH / 2, -PLATFORM_Y_OFFSET);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2f(-RAMP_WIDTH / 2, -PLATFORM_Y_OFFSET);
        GL11.glVertex2f(-RAMP_WIDTH / 2, -(RAMP_HEIGHT + PLATFORM_Y_OFFSET));
        GL11.glVertex2f(RAMP_WIDTH / 2, -(RAMP_HEIGHT + PLATFORM_Y_OFFSET));
        GL11.glVertex2f(RAMP_WIDTH / 2, -PLATFORM_Y_OFFSET);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2f(-PLATFORM_ZONE_WIDTH / 2, -PLATFORM_Y_OFFSET);
        GL11.glVertex2f(-PLATFORM_ZONE_WIDTH / 2, -(PLATFORM_ZONE_HEIGHT + PLATFORM_Y_OFFSET));
        GL11.glVertex2f(PLATFORM_ZONE_WIDTH / 2, -(PLATFORM_ZONE_HEIGHT + PLATFORM_Y_OFFSET));
        GL11.glVertex2f(PLATFORM_ZONE_WIDTH / 2, -PLATFORM_Y_OFFSET);
        GL11.glEnd();
        
        

    	GL11.glColor4fv(OPP_COLOR);

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(-PLATFORM_WIDTH / 2, PLATFORM_Y_OFFSET);
        GL11.glVertex2f(-PLATFORM_WIDTH / 2, (PLATFORM_HEIGHT + PLATFORM_Y_OFFSET));
        GL11.glVertex2f(PLATFORM_WIDTH / 2, (PLATFORM_HEIGHT + PLATFORM_Y_OFFSET));
        GL11.glVertex2f(PLATFORM_WIDTH / 2, PLATFORM_Y_OFFSET);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(-RAMP_WIDTH / 2, PLATFORM_Y_OFFSET);
        GL11.glVertex2f(-RAMP_WIDTH / 2, (RAMP_HEIGHT + PLATFORM_Y_OFFSET));
        GL11.glVertex2f(RAMP_WIDTH / 2, (RAMP_HEIGHT + PLATFORM_Y_OFFSET));
        GL11.glVertex2f(RAMP_WIDTH / 2, PLATFORM_Y_OFFSET);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(-PLATFORM_ZONE_WIDTH / 2, PLATFORM_Y_OFFSET);
        GL11.glVertex2f(-PLATFORM_ZONE_WIDTH / 2, (PLATFORM_ZONE_HEIGHT + PLATFORM_Y_OFFSET));
        GL11.glVertex2f(PLATFORM_ZONE_WIDTH / 2, (PLATFORM_ZONE_HEIGHT + PLATFORM_Y_OFFSET));
        GL11.glVertex2f(PLATFORM_ZONE_WIDTH / 2, PLATFORM_Y_OFFSET);
        GL11.glEnd();

        
    	GL11.glColor3fv(BLACK);

        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2f(-PLATFORM_WIDTH / 2, PLATFORM_Y_OFFSET);
        GL11.glVertex2f(-PLATFORM_WIDTH / 2, (PLATFORM_HEIGHT + PLATFORM_Y_OFFSET));
        GL11.glVertex2f(PLATFORM_WIDTH / 2, (PLATFORM_HEIGHT + PLATFORM_Y_OFFSET));
        GL11.glVertex2f(PLATFORM_WIDTH / 2, PLATFORM_Y_OFFSET);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2f(-RAMP_WIDTH / 2, PLATFORM_Y_OFFSET);
        GL11.glVertex2f(-RAMP_WIDTH / 2, (RAMP_HEIGHT + PLATFORM_Y_OFFSET));
        GL11.glVertex2f(RAMP_WIDTH / 2, (RAMP_HEIGHT + PLATFORM_Y_OFFSET));
        GL11.glVertex2f(RAMP_WIDTH / 2, PLATFORM_Y_OFFSET);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2f(-PLATFORM_ZONE_WIDTH / 2, PLATFORM_Y_OFFSET);
        GL11.glVertex2f(-PLATFORM_ZONE_WIDTH / 2, (PLATFORM_ZONE_HEIGHT + PLATFORM_Y_OFFSET));
        GL11.glVertex2f(PLATFORM_ZONE_WIDTH / 2, (PLATFORM_ZONE_HEIGHT + PLATFORM_Y_OFFSET));
        GL11.glVertex2f(PLATFORM_ZONE_WIDTH / 2, PLATFORM_Y_OFFSET);
        GL11.glEnd();

    	GL11.glPopMatrix();
    }

    private void doPowerCubeZone() {
    	GL11.glPushMatrix();
    	GL11.glTranslatef(FIELD_WIDTH / 2f, 0, 0);

    	GL11.glColor4fv(TEAM_COLOR);

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(-POWER_CUBE_ZONE_WIDTH / 2f, -POWER_CUBE_ZONE_HEIGHT / 2f + POWER_CUBE_ZONE_CENTER_Y);
        GL11.glVertex2f(-POWER_CUBE_ZONE_WIDTH / 2f, POWER_CUBE_ZONE_HEIGHT / 2f + POWER_CUBE_ZONE_CENTER_Y);
        GL11.glVertex2f(POWER_CUBE_ZONE_WIDTH / 2f, POWER_CUBE_ZONE_HEIGHT / 2f + POWER_CUBE_ZONE_CENTER_Y);
        GL11.glVertex2f(POWER_CUBE_ZONE_WIDTH / 2f, -POWER_CUBE_ZONE_HEIGHT / 2f + POWER_CUBE_ZONE_CENTER_Y);
        GL11.glEnd();

    	GL11.glColor4fv(OPP_COLOR);

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(-POWER_CUBE_ZONE_WIDTH / 2f, FIELD_HEIGHT - (-POWER_CUBE_ZONE_HEIGHT / 2f + POWER_CUBE_ZONE_CENTER_Y));
        GL11.glVertex2f(-POWER_CUBE_ZONE_WIDTH / 2f, FIELD_HEIGHT - (POWER_CUBE_ZONE_HEIGHT / 2f + POWER_CUBE_ZONE_CENTER_Y));
        GL11.glVertex2f(POWER_CUBE_ZONE_WIDTH / 2f, FIELD_HEIGHT - (POWER_CUBE_ZONE_HEIGHT / 2f + POWER_CUBE_ZONE_CENTER_Y));
        GL11.glVertex2f(POWER_CUBE_ZONE_WIDTH / 2f, FIELD_HEIGHT - (-POWER_CUBE_ZONE_HEIGHT / 2f + POWER_CUBE_ZONE_CENTER_Y));
        GL11.glEnd();

        
    	GL11.glColor3fv(BLACK);

        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2f(-POWER_CUBE_ZONE_WIDTH / 2f, -POWER_CUBE_ZONE_HEIGHT / 2f + POWER_CUBE_ZONE_CENTER_Y);
        GL11.glVertex2f(-POWER_CUBE_ZONE_WIDTH / 2f, POWER_CUBE_ZONE_HEIGHT / 2f + POWER_CUBE_ZONE_CENTER_Y);
        GL11.glVertex2f(POWER_CUBE_ZONE_WIDTH / 2f, POWER_CUBE_ZONE_HEIGHT / 2f + POWER_CUBE_ZONE_CENTER_Y);
        GL11.glVertex2f(POWER_CUBE_ZONE_WIDTH / 2f, -POWER_CUBE_ZONE_HEIGHT / 2f + POWER_CUBE_ZONE_CENTER_Y);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2f(-POWER_CUBE_ZONE_WIDTH / 2f, FIELD_HEIGHT - (-POWER_CUBE_ZONE_HEIGHT / 2f + POWER_CUBE_ZONE_CENTER_Y));
        GL11.glVertex2f(-POWER_CUBE_ZONE_WIDTH / 2f, FIELD_HEIGHT - (POWER_CUBE_ZONE_HEIGHT / 2f + POWER_CUBE_ZONE_CENTER_Y));
        GL11.glVertex2f(POWER_CUBE_ZONE_WIDTH / 2f, FIELD_HEIGHT - (POWER_CUBE_ZONE_HEIGHT / 2f + POWER_CUBE_ZONE_CENTER_Y));
        GL11.glVertex2f(POWER_CUBE_ZONE_WIDTH / 2f, FIELD_HEIGHT - (-POWER_CUBE_ZONE_HEIGHT / 2f + POWER_CUBE_ZONE_CENTER_Y));
        GL11.glEnd();
        
        GL11.glPopMatrix();
    }
    
    private void doRobot() {
    	GL11.glPushMatrix();
    	
    	GL11.glTranslated(robotX, robotY, 0);
    	GL11.glRotated(robotAngle * 180 / Math.PI, 0, 0, 1);
    	
    	GL11.glColor3fv(GREEN);
    	GL11.glBegin(GL11.GL_TRIANGLES);
    	GL11.glVertex2f(0, ROBOT_COM);
    	GL11.glVertex2f(ROBOT_WIDTH / 2f, ROBOT_COM - ROBOT_LENGTH);
    	GL11.glVertex2f(-ROBOT_WIDTH / 2f, ROBOT_COM - ROBOT_LENGTH);
    	GL11.glEnd();
    	
    	GL11.glColor3fv(BLACK);
    	GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2f(-ROBOT_WIDTH / 2f, ROBOT_COM);
        GL11.glVertex2f(ROBOT_WIDTH / 2f, ROBOT_COM);
    	GL11.glVertex2f(ROBOT_WIDTH / 2f, ROBOT_COM - ROBOT_LENGTH);
    	GL11.glVertex2f(-ROBOT_WIDTH / 2f, ROBOT_COM - ROBOT_LENGTH);
        GL11.glEnd();
    	
    	GL11.glPopMatrix();
    }
    
    private void doBoundary() {
    	GL11.glColor4fv(OPP_COLOR);
    	GL11.glBegin(GL11.GL_TRIANGLES);
        GL11.glVertex2f(PORTAL_WIDTH, 0);
        GL11.glVertex2f(0, PORTAL_HEIGHT);
        GL11.glVertex2f(0, 0);
        GL11.glEnd();
    	GL11.glBegin(GL11.GL_TRIANGLES);
        GL11.glVertex2f(FIELD_WIDTH - PORTAL_WIDTH, 0);
        GL11.glVertex2f(FIELD_WIDTH, PORTAL_HEIGHT);
        GL11.glVertex2f(FIELD_WIDTH, 0);
        GL11.glEnd();

    	GL11.glColor4fv(TEAM_COLOR);
    	GL11.glBegin(GL11.GL_TRIANGLES);
        GL11.glVertex2f(PORTAL_WIDTH, FIELD_HEIGHT);
        GL11.glVertex2f(0, FIELD_HEIGHT - PORTAL_HEIGHT);
        GL11.glVertex2f(0, FIELD_HEIGHT);
        GL11.glEnd();
    	GL11.glBegin(GL11.GL_TRIANGLES);
        GL11.glVertex2f(FIELD_WIDTH - PORTAL_WIDTH, FIELD_HEIGHT);
        GL11.glVertex2f(FIELD_WIDTH, FIELD_HEIGHT - PORTAL_HEIGHT);
        GL11.glVertex2f(FIELD_WIDTH, FIELD_HEIGHT);
        GL11.glEnd();
    	
    	GL11.glColor3fv(BLACK);
    	GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2f(PORTAL_WIDTH, 0);
        GL11.glVertex2f(FIELD_WIDTH - PORTAL_WIDTH, 0);
        GL11.glVertex2f(FIELD_WIDTH, PORTAL_HEIGHT);
        GL11.glVertex2f(FIELD_WIDTH, FIELD_HEIGHT - PORTAL_HEIGHT);
        GL11.glVertex2f(FIELD_WIDTH - PORTAL_WIDTH, FIELD_HEIGHT - (FIELD_HEIGHT / windowHeight));
        GL11.glVertex2f(PORTAL_WIDTH, FIELD_HEIGHT - (FIELD_HEIGHT / windowHeight));
        GL11.glVertex2f(0, FIELD_HEIGHT - PORTAL_HEIGHT);
        GL11.glVertex2f(0, PORTAL_HEIGHT);
        GL11.glEnd();
    }
    
    @Override
    public void run() {
        GLFW.glfwMakeContextCurrent(id);
        GLFW.glfwSwapInterval(1);
        GLFW.glfwShowWindow(id);
        GL.createCapabilities();
        GLFW.glfwSetWindowSizeCallback(id, this::windowResized);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glClearColor(1, 1, 1, 1);
        while (!GLFW.glfwWindowShouldClose(id)) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            render();
            GLFW.glfwSwapBuffers(id);
            GLFW.glfwPollEvents();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        Callbacks.glfwFreeCallbacks(id);
        GLFW.glfwDestroyWindow(id);
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }

    private void keyClicked(long window, int key, int scancode, int action, int mods) {
        if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) {
            GLFW.glfwSetWindowShouldClose(window, true);
        }
    }

    private void windowResized(long window, int width, int height) {
    	windowHeight = height;
        GL11.glViewport(0, 0, width, height);
    }

    public Window() throws Exception {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
        id = GLFW.glfwCreateWindow(windowHeight / 2, windowHeight, "Robot Minimap", MemoryUtil.NULL, MemoryUtil.NULL);
        if (id == MemoryUtil.NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }
        GLFW.glfwSetKeyCallback(id, this::keyClicked);
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            GLFW.glfwGetWindowSize(id, width, height);
            GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
            GLFW.glfwSetWindowPos(id, (vidmode.width() - width.get(0)) / 2, (vidmode.height() - height.get(0)) / 2);
        }
        client = new MinimapCoordsClient();
        client.start();
    }
}
